package com.example.login;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Base64;

public class User {
    public String hashedPassword, salt;
    public byte[] encryptedUsername, encryptedEmail;
    public String key;
    private static final String UNICODE_FORMAT = "UTF-8";

    public User() {
    }

    public User(String username, String password, String email) {
        try {
            key = generateKey("AES");
            Cipher cipher = Cipher.getInstance("AES");
            this.encryptedUsername = encryptString(username, key, cipher);
            this.encryptedEmail = encryptString(email, key, cipher);
        } catch (Exception e) {
            e.printStackTrace();
            this.encryptedUsername = null;
            this.encryptedEmail = null;
        }
        this.salt = BCrypt.gensalt(10);
        this.hashedPassword = hash(password);
    }

    public User(String key, String username, String password, String email, String salt) {
        try {
            this.key = key;
            this.encryptedUsername = username.getBytes(UNICODE_FORMAT);
            this.salt = salt;
            this.hashedPassword = hash(password);
            this.encryptedEmail = email.getBytes(UNICODE_FORMAT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String hash(String password) {
        return BCrypt.hashpw(password, salt);
    }

    private String generateKey(String encryptionType) {
            try {
                SecretKey myKey = KeyGenerator.getInstance(encryptionType).generateKey();
                return convertKeytostring(myKey);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
    }

    private byte[] encryptString(String dataToEncrypt, String myKey, Cipher cipher) {
        try {
            SecretKey key = convertStringToKey(myKey);

            byte[] text = dataToEncrypt.getBytes(UNICODE_FORMAT);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] textEncrypted = cipher.doFinal(text);
            return textEncrypted;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decryptString(byte[] dataToDecrypt, String myKey, Cipher cipher) {
        try {
            SecretKey key = convertStringToKey(myKey);

            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] textDecrypted = cipher.doFinal(dataToDecrypt);
            String result = new String(textDecrypted);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // good
    public byte[] getEncryptedUsername() {
        return encryptedUsername;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public byte[] getEncryptedEmail() {
        return encryptedEmail;
    }

    public String getSalt() {
        return salt;
    }

    public String getKey() {
        return key;
    }

    public static SecretKey convertStringToKey(String encodedKey) {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        return originalKey;
    }

    public static String convertKeytostring(SecretKey key) {
        String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
        return encodedKey;
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
}
