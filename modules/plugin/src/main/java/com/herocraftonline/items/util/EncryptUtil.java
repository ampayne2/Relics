/*
 * This file is part of Relics.
 *
 * Copyright (c) 2017, Austin Payne <amperialdev@gmail.com - http://github.com/Amperial>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

/**
 * Basic AES string encryption and decryption methods.
 *
 * @author Austin Payne
 */
public final class EncryptUtil {

    private static final String ALGORITHM = "AES";
    private static Key KEY;

    static {
        try {
            KEY = KeyGenerator.getInstance(ALGORITHM).generateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private EncryptUtil() {
    }

    public static String getKey() {
        return Base64.getEncoder().encodeToString(KEY.getEncoded());
    }

    public static void setKey(String key) {
        byte[] decoded = Base64.getDecoder().decode(key);
        KEY = new SecretKeySpec(decoded, 0, decoded.length, ALGORITHM);
    }

    public static String encrypt(String string) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, KEY);
            return Base64.getEncoder().encodeToString(cipher.doFinal(string.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decrypt(String string) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, KEY);
            return new String(cipher.doFinal(Base64.getDecoder().decode(string)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
