package io.bastillion.common.util

import io.bastillion.manage.util.EncryptionUtil
import org.apache.commons.lang3.StringUtils

data class Config(
        //set to true to regenerate and import SSH keys
        val resetApplicationSSHKey: Boolean = false,

        //SSH key type 'dsa', 'rsa', or 'ecdsa' for generated keys
        private val sshKeyType: String = "rsa",

        //SSH key length for generated keys. 2048 => 'rsa','dsa'; 521 => 'ecdsa'
        private val sshKeyLength: Int = 2048,

        //private ssh key, leave blank to generate key pair
        private val privateKey: String? = null,

        //public ssh key, leave blank to generate key pair
        val publicKey: String? = null,

        //default passphrase, leave blank for key without passphrase
        val defaultSSHPassphrase: String = "\${randomPassphrase}",

        //enable audit
        val enableInternalAudit: Boolean = false,

        //keep audit logs for in days
        val deleteAuditLogAfter: Int = 90,

        //The number of seconds that the client will wait before sending a null packet to the server to keep the connection alive
        val serverAliveInterval: Int = 60,

        //default timeout in minutes for websocket connection (no timeout for <=0)
        val websocketTimeout: Int = 0,

        //enable SSH agent forwarding
        val agentForwarding: Boolean = false,

        //enable two-factor authentication with a one-time password - 'required', 'optional', or 'disabled'
        val oneTimePassword: String = "optional",

        //set to false to disable key management. If false, the Bastillion public key will be appended to the authorized_keys file (instead of it being overwritten completely).
        val keyManagementEnabled: Boolean = true,

        //set to true to generate keys when added/managed by users and enforce strong passphrases set to false to allow users to set their own public key
        val forceUserKeyGeneration: Boolean = true,

        //authorized_keys refresh interval in minutes (no refresh for <=0)
        val authKeysRefreshInterval: Int = 120,

        //Regular expression to enforce password policy
        val passwordComplexityRegEx: String = "((?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[!@",

        //Password complexity error message
        val passwordComplexityMsg: String = "Passwords must be 8 to 20 characters, contain one digit, one lowercase, one uppercase, and one special character",

        //Expire inactive user accounts after x many days. Set to <=0 to disable
        val accountExpirationDays: Int = -1,

        //HTTP header to identify client IP Address - 'X-FORWARDED-FOR'
        val clientIPHeader: String? = null,

        //specify a external authentication module (ex: ldap-ol, ldap-ad).  Edit the jaas.conf to set connection details
        val jaasModule: String? = null,

        //Default profile for all authenticated LDAP users
        val defaultProfileForLdap: String? = null,

        //The session time out value of application in minutes
        val sessionTimeout: Int = 15,
        /*
        Database and connection pool settings
         */

        /*
        Database and connection pool settings
         */
        //Database user
        val dbUser: String = "bastillion",

        //Database password
        val dbPassword: String = "",

        //Database JDBC driver
        val dbDriver: String = "org.h2.Driver",

        //Connection URL to the DB
        val dbConnectionURL: String = "jdbc:h2:keydb/bastillion;CIPHER=AES;",

        //Max connections in the connection pool
        val maxActive: Int = 25,

        //When true, objects will be validated before being returned by the connection pool
        val testOnBorrow: Boolean = true,

        //The minimum number of objects allowed in the connection pool before spawning new ones
        val minIdle: Int = 2,

        //The maximum amount of time (in milliseconds) to block before throwing an exception when the connection pool is exhausted
        val maxWait: Int = 15000
) {
    fun decryptProperty(input: String): String {
        if (StringUtils.isNotEmpty(input)) {
            return EncryptionUtil.decrypt(
                    input.replace("^" + EncryptionUtil.CRYPT_ALGORITHM + "\\{", "").replace("\\}$", "")
            );

        }
        return "";
    }
    //	public static void encryptProperty(String name, String value) {
//		//remove property
//		if (StringUtils.isNotEmpty(value)) {
//			try {
//				prop.setProperty(name, EncryptionUtil.CRYPT_ALGORITHM + "{" + EncryptionUtil.encrypt(value) + "}");
//				prop.save();
//			} catch (Exception ex) {
//				log.error(ex.toString(), ex);
//			}
//		}
//	}
}