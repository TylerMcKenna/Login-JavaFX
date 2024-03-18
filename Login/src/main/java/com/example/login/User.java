package com.example.login;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.mindrot.jbcrypt.BCrypt;

public class User {
    public String encryptedUsername, hashedPassword, encryptedEmail, salt;
/*[ {
  "encryptedUsername" : null,
  "hashedPassword" : "$2a$10$zCXQSGiJbH/I0fp36cJA6u5eZ8FH9NNddZ7uVx/DvWYD9q2wPjeDW",
  "encryptedEmail" : null,
  "salt" : "$2a$10$zCXQSGiJbH/I0fp36cJA6u"
}
  {
  "encryptedUsername" : null,
  "hashedPassword" : "$2a$10$dME2/DzRTte8WvKBcp7ZcuJzttxbaIaq8XcXuu95kOSmlz.yx2sPW",
  "encryptedEmail" : null,
  "salt" : "$2a$10$dME2/DzRTte8WvKBcp7Zcu"
} ]*/
    public User() {

    }

    public User(String username, String password, String email) {
        this.encryptedUsername = username;
        this.salt = BCrypt.gensalt(10);
        this.hashedPassword = hash(password);
        this.encryptedEmail = email;
    }

    public User(String username, String password, String email, String salt) {
        this.encryptedUsername = username;
        this.salt = salt;
        this.hashedPassword = hash(password);
        this.encryptedEmail = email;
    }

    private String hash(String password) {
        return BCrypt.hashpw(password, salt);
    }

    // How to use idk yet
    public boolean checkPassword(String password) {
        //return BCrypt.hashpw()
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "encryptedUsername='" + encryptedUsername + '\'' +
                ", hashedPassword='" + hashedPassword + '\'' +
                ", encryptedEmail='" + encryptedEmail + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }

    public String getEncryptedUsername() {
        return encryptedUsername;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getEncryptedEmail() {
        return encryptedEmail;
    }

    public String getSalt() {
        return salt;
    }
}
