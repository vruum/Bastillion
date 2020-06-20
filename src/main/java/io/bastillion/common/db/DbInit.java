/**
 * Copyright (C) 2013 Loophole, LLC
 * <p>
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 * As a special exception, the copyright holders give permission to link the
 * code of portions of this program with the OpenSSL library under certain
 * conditions as described in each individual source file and distribute
 * linked combinations including the program with the OpenSSL library. You
 * must comply with the GNU Affero General Public License in all respects for
 * all of the code used other than as permitted herein. If you modify file(s)
 * with this exception, you may extend this exception to your version of the
 * file(s), but you are not obligated to do so. If you do not wish to do so,
 * delete this exception statement from your version. If you delete this
 * exception statement from all source files in the program, then also delete
 * it in the license file.
 */
package io.bastillion.common.db;

import io.bastillion.common.util.AppConfig;
import io.bastillion.manage.model.Auth;
import io.bastillion.manage.util.*;

import javax.servlet.ServletException;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbInit {

    private static Logger log = LoggerFactory.getLogger(DbInit.class);

    public void init() throws ServletException {


        Connection connection = null;
        Statement statement = null;
        //check if reset ssh application key is set
        boolean resetSSHKey = "true".equals(AppConfig.getProperty("resetApplicationSSHKey"));

        //if DB password is empty generate a random
        if (StringUtils.isEmpty(AppConfig.getProperty("dbPassword"))) {
            String dbPassword = null;
            String dbPasswordConfirm = null;
            if (!"true".equals(System.getProperty("GEN_DB_PASS"))) {
                //prompt for password and confirmation
                while (dbPassword == null || !dbPassword.equals(dbPasswordConfirm)) {
                    if (System.console() == null) {
                        Scanner in = new Scanner(System.in);
                        System.out.println("Please enter database password: ");
                        dbPassword = in.nextLine();
                        System.out.println("Please confirm database password: ");
                        dbPasswordConfirm = in.nextLine();
                    } else {
                        dbPassword = new String(System.console().readPassword("Please enter database password: "));
                        dbPasswordConfirm = new String(System.console().readPassword("Please confirm database password: "));
                    }
                    if (!dbPassword.equals(dbPasswordConfirm)) {
                        System.out.println("Passwords do not match");
                    }
                }
            }
            //set password
            if (StringUtils.isNotEmpty(dbPassword)) {
                AppConfig.encryptProperty("dbPassword", dbPassword);
                //if password not set generate a random
            } else {
                System.out.println("Generating random database password");
                AppConfig.encryptProperty("dbPassword", RandomStringUtils.random(32, true, true));
            }
            //else encrypt password if plain-text
        } else if (!AppConfig.isPropertyEncrypted("dbPassword")) {
            AppConfig.encryptProperty("dbPassword", AppConfig.getProperty("dbPassword"));
        }

        try {
            connection = DBUtils.getConn();
            statement = connection.createStatement();

            ResultSet rs = statement.executeQuery("select * from information_schema.tables where upper(table_name) = 'USERS' and table_schema='PUBLIC'");
            if (!rs.next()) {
                resetSSHKey = true;

                //create DB objects
                statement.executeUpdate("create table if not exists users (id INTEGER PRIMARY KEY AUTO_INCREMENT, first_nm varchar, last_nm varchar, email varchar, username varchar not null unique, password varchar, auth_token varchar, auth_type varchar not null default '" + Auth.AUTH_BASIC + "', user_type varchar not null default '" + Auth.ADMINISTRATOR + "', salt varchar, otp_secret varchar, last_login_tm timestamp, expiration_tm timestamp)");
                statement.executeUpdate("create table if not exists user_theme (user_id INTEGER PRIMARY KEY, bg varchar(7), fg varchar(7), d1 varchar(7), d2 varchar(7), d3 varchar(7), d4 varchar(7), d5 varchar(7), d6 varchar(7), d7 varchar(7), d8 varchar(7), b1 varchar(7), b2 varchar(7), b3 varchar(7), b4 varchar(7), b5 varchar(7), b6 varchar(7), b7 varchar(7), b8 varchar(7), foreign key (user_id) references users(id) on delete cascade) ");
                statement.executeUpdate("create table if not exists system (id INTEGER PRIMARY KEY AUTO_INCREMENT, display_nm varchar not null, user varchar not null, host varchar not null, port INTEGER not null, authorized_keys varchar not null, status_cd varchar not null default 'INITIAL')");
                statement.executeUpdate("create table if not exists profiles (id INTEGER PRIMARY KEY AUTO_INCREMENT, nm varchar not null, desc varchar not null)");
                statement.executeUpdate("create table if not exists system_map (profile_id INTEGER, system_id INTEGER, foreign key (profile_id) references profiles(id) on delete cascade , foreign key (system_id) references system(id) on delete cascade, primary key (profile_id, system_id))");
                statement.executeUpdate("create table if not exists user_map (user_id INTEGER, profile_id INTEGER, foreign key (user_id) references users(id) on delete cascade, foreign key (profile_id) references profiles(id) on delete cascade, primary key (user_id, profile_id))");
                statement.executeUpdate("create table if not exists application_key (id INTEGER PRIMARY KEY AUTO_INCREMENT, public_key varchar not null, private_key varchar not null, passphrase varchar)");

                statement.executeUpdate("create table if not exists status (id INTEGER, user_id INTEGER, status_cd varchar not null default 'INITIAL', foreign key (id) references system(id) on delete cascade, foreign key (user_id) references users(id) on delete cascade, primary key(id, user_id))");
                statement.executeUpdate("create table if not exists scripts (id INTEGER PRIMARY KEY AUTO_INCREMENT, user_id INTEGER, display_nm varchar not null, script varchar not null, foreign key (user_id) references users(id) on delete cascade)");


                statement.executeUpdate("create table if not exists public_keys (id INTEGER PRIMARY KEY AUTO_INCREMENT, key_nm varchar not null, type varchar, fingerprint varchar, public_key varchar, enabled boolean not null default true, create_dt timestamp not null default CURRENT_TIMESTAMP(), user_id INTEGER, profile_id INTEGER, foreign key (profile_id) references profiles(id) on delete cascade, foreign key (user_id) references users(id) on delete cascade)");

                statement.executeUpdate("create table if not exists session_log (id BIGINT PRIMARY KEY AUTO_INCREMENT, session_tm timestamp default CURRENT_TIMESTAMP, first_nm varchar, last_nm varchar, username varchar not null, ip_address varchar)");
                statement.executeUpdate("create table if not exists terminal_log (session_id BIGINT, instance_id INTEGER, output varchar not null, log_tm timestamp default CURRENT_TIMESTAMP, display_nm varchar not null, user varchar not null, host varchar not null, port INTEGER not null, foreign key (session_id) references session_log(id) on delete cascade)");

                //if exists readfile to set default password
                String salt = EncryptionUtil.generateSalt();
                String defaultPassword = EncryptionUtil.hash("changeme" + salt);

                //set password if running in EC2
                File file = new File("/opt/bastillion/instance_id");
                if (file.exists()) {
                    String str = FileUtils.readFileToString(file, "UTF-8");
                    if (StringUtils.isNotEmpty(str)) {
                        defaultPassword = EncryptionUtil.hash(str.trim() + salt);
                    }
                }

                //insert default admin user
                PreparedStatement pStmt = connection.prepareStatement("insert into users (username, password, user_type, salt) values(?,?,?,?)");
                pStmt.setString(1, "admin");
                pStmt.setString(2, defaultPassword);
                pStmt.setString(3, Auth.MANAGER);
                pStmt.setString(4, salt);
                pStmt.execute();
                DBUtils.closeStmt(pStmt);

            }
            DBUtils.closeRs(rs);

            //if reset ssh application key then generate new key
            if (resetSSHKey) {

                //delete old key entry
                PreparedStatement pStmt = connection.prepareStatement("delete from application_key");
                pStmt.execute();
                DBUtils.closeStmt(pStmt);

                //generate new key and insert passphrase
                System.out.println("Setting Bastillion SSH public/private key pair");

                //generate application pub/pvt key and get values
                String passphrase = SSHUtil.keyGen();
                String publicKey = SSHUtil.getPublicKey();
                String privateKey = SSHUtil.getPrivateKey();

                //insert new keys
                pStmt = connection.prepareStatement("insert into application_key (public_key, private_key, passphrase) values(?,?,?)");
                pStmt.setString(1, publicKey);
                pStmt.setString(2, EncryptionUtil.encrypt(privateKey));
                pStmt.setString(3, EncryptionUtil.encrypt(passphrase));
                pStmt.execute();
                DBUtils.closeStmt(pStmt);

                System.out.println("Bastillion Generated Global Public Key:");
                System.out.println(publicKey);

                //set config to default
                AppConfig.updateProperty("publicKey", "");
                AppConfig.updateProperty("privateKey", "");
                AppConfig.updateProperty("defaultSSHPassphrase", "${randomPassphrase}");

                //set to false
                AppConfig.updateProperty("resetApplicationSSHKey", "false");

            }

            //delete ssh keys
            SSHUtil.deletePvtGenSSHKey();


        } catch (Exception ex) {
            log.error(ex.toString(), ex);
        } finally {
            DBUtils.closeStmt(statement);
            DBUtils.closeConn(connection);
        }


        RefreshAuthKeyUtil.startRefreshAllSystemsTimerTask();
    }

}
