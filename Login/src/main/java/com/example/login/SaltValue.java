package com.example.login;

public class SaltValue {
    private String salt;
    private String value;

    public SaltValue(String salt, String value) {
        this.salt = salt;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getSalt() {
        return salt;
    }
}