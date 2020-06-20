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
    private final boolean agentForwarding = false;
    private final String oneTimePassword = "optional";
    private final boolean keyManagementEnabled = true;
    private final boolean forceUserKeyGeneration = true;
    private final int authKeysRefreshInterval = 120;
    private final String passwordComplexityRegEx = "((?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[!@";
    private final String passwordComplexityMsg = "Passwords must be 8 to 20 characters, contain one digit, one lowercase, one uppercase, and one special character";
    private final int accountExpirationDays = -1;
    private final String clientIPHeader = ;
    private final String jaasModule = ;
    private final String defaultProfileForLdap = ;
    private final int sessionTimeout = 15;
    private final String dbUser = "bastillion";
    private final String dbPassword = "";
    private final String dbDriver = "org.h2.Driver";
    private final String dbConnectionURL = "jdbc:h2:keydb/bastillion;CIPHER=AES;";
    private final int maxActive = 25;
    private final boolean testOnBorrow = true;
    private final int minIdle = 2;
    private final int maxWait = 15000;

}
