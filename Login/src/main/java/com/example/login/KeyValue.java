package com.example.login;

import javax.crypto.SecretKey;
import java.net.SecureCacheResponse;

public class KeyValue {
    private SecretKey key;
    private byte[] value;

    public KeyValue(SecretKey key, byte[] value) {
        this.key = key;
        this.value = value;
    }

    public byte[] getValue() {
        return value;
    }

    public SecretKey getKey() {
        return key;
    }
}
