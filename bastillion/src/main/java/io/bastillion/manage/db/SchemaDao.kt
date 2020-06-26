package io.bastillion.manage.db

import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

interface SchemaDao {
    @SqlUpdate("create table if not exists users (id INTEGER PRIMARY KEY AUTO_INCREMENT, first_nm varchar, last_nm varchar, email varchar, username varchar not null unique, password varchar, auth_token varchar, auth_type varchar not null default '\" + Auth.AUTH_BASIC + \"', user_type varchar not null default '\" + Auth.ADMINISTRATOR + \"', salt varchar, otp_secret varchar, last_login_tm timestamp, expiration_tm timestamp)")
    fun createUsers()

    @SqlUpdate("create table if not exists logins (username VARCHAR PRIMARY KEY, hashed_password varchar, user_id INTEGER, last_login_tm timestamp, created timestamp)")
    fun createLogins()

    @SqlUpdate("create table if not exists user_theme (user_id INTEGER PRIMARY KEY, bg varchar(7), fg varchar(7), d1 varchar(7), d2 varchar(7), d3 varchar(7), d4 varchar(7), d5 varchar(7), d6 varchar(7), d7 varchar(7), d8 varchar(7), b1 varchar(7), b2 varchar(7), b3 varchar(7), b4 varchar(7), b5 varchar(7), b6 varchar(7), b7 varchar(7), b8 varchar(7), foreign key (user_id) references users(id) on delete cascade) ")
    fun createUserThemes()

    @SqlUpdate("create table if not exists system (id INTEGER PRIMARY KEY AUTO_INCREMENT, display_nm varchar not null, user varchar not null, host varchar not null, port INTEGER not null, authorized_keys varchar not null, status_cd varchar not null default 'INITIAL')")
    fun createSystems()

    @SqlUpdate("create table if not exists profiles (id INTEGER PRIMARY KEY AUTO_INCREMENT, nm varchar not null, desc varchar not null)")
    fun createProfiles()

    @SqlUpdate("create table if not exists system_map (profile_id INTEGER, system_id INTEGER, foreign key (profile_id) references profiles(id) on delete cascade , foreign key (system_id) references system(id) on delete cascade, primary key (profile_id, system_id))")
    fun createSystemsMap()

    @SqlUpdate("create table if not exists user_map (user_id INTEGER, profile_id INTEGER, foreign key (user_id) references users(id) on delete cascade, foreign key (profile_id) references profiles(id) on delete cascade, primary key (user_id, profile_id))")
    fun createUsersMap()

    @SqlUpdate("create table if not exists application_key (id INTEGER PRIMARY KEY AUTO_INCREMENT, public_key varchar not null, private_key varchar not null, passphrase varchar)")
    fun createApplicationKey()

    @SqlUpdate("create table if not exists status (id INTEGER, user_id INTEGER, status_cd varchar not null default 'INITIAL', foreign key (id) references system(id) on delete cascade, foreign key (user_id) references users(id) on delete cascade, primary key(id, user_id))")
    fun createStatus()

    @SqlUpdate("create table if not exists scripts (id INTEGER PRIMARY KEY AUTO_INCREMENT, user_id INTEGER, display_nm varchar not null, script varchar not null, foreign key (user_id) references users(id) on delete cascade)")
    fun createScripts()

    @SqlUpdate("create table if not exists public_keys (id INTEGER PRIMARY KEY AUTO_INCREMENT, key_nm varchar not null, type varchar, fingerprint varchar, public_key varchar, enabled boolean not null default true, create_dt timestamp not null default CURRENT_TIMESTAMP(), user_id INTEGER, profile_id INTEGER, foreign key (profile_id) references profiles(id) on delete cascade, foreign key (user_id) references users(id) on delete cascade)")
    fun createPublicKeys()

    @SqlUpdate("create table if not exists session_log (id BIGINT PRIMARY KEY AUTO_INCREMENT, session_tm timestamp default CURRENT_TIMESTAMP, first_nm varchar, last_nm varchar, username varchar not null, ip_address varchar)")
    fun createSystemsLogs()

    @SqlUpdate("create table if not exists terminal_log (session_id BIGINT, instance_id INTEGER, output varchar not null, log_tm timestamp default CURRENT_TIMESTAMP, display_nm varchar not null, user varchar not null, host varchar not null, port INTEGER not null, foreign key (session_id) references session_log(id) on delete cascade)")
    fun createTerminalLogs()

    @SqlUpdate("drop table IF EXISTS users CASCADE")
    fun deleteUsers()

    @SqlUpdate("drop table IF EXISTS logins CASCADE")
    fun deleteLogins()

    @SqlUpdate("drop table IF EXISTS user_theme CASCADE")
    fun deleteUserThemes()

    @SqlUpdate("drop table IF EXISTS system CASCADE")
    fun deleteSystems()

    @SqlUpdate("drop table IF EXISTS profiles CASCADE")
    fun deleteProfiles()

    @SqlUpdate("drop table IF EXISTS system_map CASCADE")
    fun deleteSystemsMap()

    @SqlUpdate("drop table IF EXISTS user_map CASCADE")
    fun deleteUsersMap()

    @SqlUpdate("drop table IF EXISTS application_key CASCADE")
    fun deleteApplicationKey()

    @SqlUpdate("drop table IF EXISTS status CASCADE")
    fun deleteStatus()

    @SqlUpdate("drop table IF EXISTS scripts CASCADE")
    fun deleteScripts()

    @SqlUpdate("drop table IF EXISTS public_keys CASCADE")
    fun deletePublicKeys()

    @SqlUpdate("drop table IF EXISTS session_log CASCADE")
    fun deleteSystemsLogs()

    @SqlUpdate("drop table IF EXISTS terminal_log CASCADE")
    fun deleteTerminalLogs()
}