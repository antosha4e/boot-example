package com.bootexample.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created: antosha4e
 * Date: 16.05.16
 */
public class PasswordEncryptor {
    private static final String SALT = "123";

    public static String encryptPassword(String password) {
        return getHash(password + SALT);
    }

    public static boolean samePassword(String passwordPlain, String passwordHash) {
        return getHash(passwordPlain + SALT).equals(passwordHash);
    }

    private static String getHash(String str) {
        byte byteData[] = getHashBytes(str);
        if(byteData == null) {
            return str;
        }

        StringBuilder sb = new StringBuilder();
        for (byte aByteData1 : byteData) {
            sb.append(Integer.toString((aByteData1 & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    private static byte[] getHashBytes(String str) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }

        md.update(str.getBytes());

        return md.digest();
    }
}