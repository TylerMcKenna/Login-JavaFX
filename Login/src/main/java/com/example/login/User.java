package com.example.login;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.mindrot.jbcrypt.BCrypt;

import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;

public class User {
    private static int currentID = 0;

    private int id;

    private String name, nameKey, email, emailKey, password, passwordSalt;

    public User(String name, String email, String password) {
        try {
            this.id = currentID;
            currentID++;

            KeyValue nameKeyVal = getEncryption(name);
            this.name = nameKeyVal.getValue().toString();
            this.nameKey = Base64.getEncoder().encodeToString(nameKeyVal.getKey().getEncoded());

            KeyValue emailKeyVal = getEncryption(name);
            this.email = emailKeyVal.getValue().toString();
            this.emailKey = Base64.getEncoder().encodeToString(emailKeyVal.getKey().getEncoded());

            SaltValue passwordSaltVal = getHash(password);
            this.password = passwordSaltVal.getValue();
            this.passwordSalt = passwordSaltVal.getSalt();
        } catch (Exception e) {
            System.out.println("I'm cooked");
            e.printStackTrace();
            System.exit(0);
        }
    }

    private KeyValue getEncryption(String text) {
        try {
            // Generates a SecretKey using AES
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256);
            SecretKey secretKey = keyGen.generateKey();

            // Create cipher, set its mode, and pass it a key.
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            // Converts text to a byte array and encrypts
            byte[] plaintext = text.getBytes();
            byte[] ciphertext = cipher.doFinal(plaintext);

            return new KeyValue(secretKey, ciphertext);
        } catch (Exception e) {
            System.out.println("I'm cooked");
            System.exit(0);
            return null;
        }
    }

    /*
if (BCrypt.checkpw(candidate, hashed))
    System.out.println("It matches");
else
    System.out.println("It does not match");*/
    private SaltValue getHash(String password) {
        String salt = BCrypt.gensalt();
        return new SaltValue(salt, BCrypt.hashpw(password, salt));
    }

    private String getDecryption(KeyValue encrypted) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, encrypted.getKey());

            byte[] decryptedText = cipher.doFinal(encrypted.getValue());
            String text = new String(decryptedText, StandardCharsets.UTF_8);
            return text;
        } catch (Exception e) {
            System.out.println("I'm cooked");
            System.exit(0);
            return null;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameKey() {
        return nameKey;
    }

    public void setNameKey(String nameKey) {
        this.nameKey = nameKey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailKey() {
        return emailKey;
    }

    public void setEmailKey(String emailKey) {
        this.emailKey = emailKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public int getId() {
        return id;
    }

    public static void setCurrentID(int currentID) {
        User.currentID = currentID;
    }
}
