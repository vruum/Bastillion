package io.bastillion.common.util;

public class Config {
    //set to true to regenerate and import SSH keys
    private final boolean resetApplicationSSHKey = false;
    //SSH key type 'dsa', 'rsa', or 'ecdsa' for generated keys
    private final String sshKeyType = "rsa";
    //SSH key length for generated keys. 2048 => 'rsa','dsa'; 521 => 'ecdsa'
    private final int sshKeyLength = 2048;
    //private ssh key, leave blank to generate key pair
    private final String privateKey;
    //public ssh key, leave blank to generate key pair
    private final String publicKey;
    //default passphrase, leave blank for key without passphrase
    private final String defaultSSHPassphrase = "${randomPassphrase}";
    //enable audit
    private final boolean enableInternalAudit = false;
    //keep audit logs for in days
    private final int deleteAuditLogAfter = 90;
    //The number of seconds that the client will wait before sending a null packet to the server to keep the connection alive
    private final int serverAliveInterval = 60;
    //default timeout in minutes for websocket connection (no timeout for <=0)
    private final int websocketTimeout = 0;
    //enable SSH agent forwarding
    private final boolean agentForwarding = false;
    //enable two-factor authentication with a one-time password - 'required', 'optional', or 'disabled'
    private final String oneTimePassword = "optional";
    //set to false to disable key management. If false, the Bastillion public key will be appended to the authorized_keys file (instead of it being overwritten completely).
    private final boolean keyManagementEnabled = true;
    //set to true to generate keys when added/managed by users and enforce strong passphrases set to false to allow users to set their own public key
    private final boolean forceUserKeyGeneration = true;
    //authorized_keys refresh interval in minutes (no refresh for <=0)
    private final int authKeysRefreshInterval = 120;
    //Regular expression to enforce password policy
    private final String passwordComplexityRegEx = "((?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[!@";
    //Password complexity error message
    private final String passwordComplexityMsg = "Passwords must be 8 to 20 characters, contain one digit, one lowercase, one uppercase, and one special character";
    //Expire inactive user accounts after x many days. Set to <=0 to disable
    private final int accountExpirationDays = -1;
    //HTTP header to identify client IP Address - 'X-FORWARDED-FOR'
    private final String clientIPHeader = ;
    //specify a external authentication module (ex: ldap-ol, ldap-ad).  Edit the jaas.conf to set connection details
    private final String jaasModule = ;
    //Default profile for all authenticated LDAP users
    private final String defaultProfileForLdap = ;
    //The session time out value of application in minutes
    private final int sessionTimeout = 15;
    /*
    Database and connection pool settings
     */

    //Database user
    private final String dbUser = "bastillion";
    //Database password
    private final String dbPassword = "";
    //Database JDBC driver
    private final String dbDriver = "org.h2.Driver";
    //Connection URL to the DB
    private final String dbConnectionURL = "jdbc:h2:keydb/bastillion;CIPHER=AES;";
    //Max connections in the connection pool
    private final int maxActive = 25;
    //When true, objects will be validated before being returned by the connection pool
    private final boolean testOnBorrow = true;
    //The minimum number of objects allowed in the connection pool before spawning new ones
    private final int minIdle = 2;
    //The maximum amount of time (in milliseconds) to block before throwing an exception when the connection pool is exhausted
    private final int maxWait = 15000;

    public boolean isResetApplicationSSHKey() {
        return resetApplicationSSHKey;
    }

    public String getSshKeyType() {
        return sshKeyType;
    }

    public int getSshKeyLength() {
        return sshKeyLength;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getDefaultSSHPassphrase() {
        return defaultSSHPassphrase;
    }

    public boolean isEnableInternalAudit() {
        return enableInternalAudit;
    }

    public int getDeleteAuditLogAfter() {
        return deleteAuditLogAfter;
    }

    public int getServerAliveInterval() {
        return serverAliveInterval;
    }

    public int getWebsocketTimeout() {
        return websocketTimeout;
    }

    public boolean isAgentForwarding() {
        return agentForwarding;
    }

    public String getOneTimePassword() {
        return oneTimePassword;
    }

    public boolean isKeyManagementEnabled() {
        return keyManagementEnabled;
    }

    public boolean isForceUserKeyGeneration() {
        return forceUserKeyGeneration;
    }

    public int getAuthKeysRefreshInterval() {
        return authKeysRefreshInterval;
    }

    public String getPasswordComplexityRegEx() {
        return passwordComplexityRegEx;
    }

    public String getPasswordComplexityMsg() {
        return passwordComplexityMsg;
    }

    public int getAccountExpirationDays() {
        return accountExpirationDays;
    }

    public String getClientIPHeader() {
        return clientIPHeader;
    }
}
