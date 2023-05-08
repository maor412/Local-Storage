package com.maor.localstorage;
import android.os.Build;

import androidx.annotation.RequiresApi;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class EncryptionUtils {
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final int KEY_LENGTH = 256;
    private static final int ITERATIONS = 10000;
    private static final int SALT_LENGTH = 32;
    private static final int IV_LENGTH = 16;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String encrypt(String input, String password) throws Exception {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);

        byte[] iv = new byte[IV_LENGTH];
        random.nextBytes(iv);

        SecretKeySpec keySpec = generateKeyFromPassword(password, salt);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv));

        byte[] encryptedBytes = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));

        byte[] combined = new byte[iv.length + salt.length + encryptedBytes.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(salt, 0, combined, iv.length, salt.length);
        System.arraycopy(encryptedBytes, 0, combined, iv.length + salt.length, encryptedBytes.length);

        return Base64.getEncoder().encodeToString(combined);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String decrypt(String input, String password) throws Exception {
        byte[] combined = Base64.getDecoder().decode(input);

        byte[] iv = Arrays.copyOfRange(combined, 0, IV_LENGTH);
        byte[] salt = Arrays.copyOfRange(combined, IV_LENGTH, IV_LENGTH + SALT_LENGTH);
        byte[] encryptedBytes = Arrays.copyOfRange(combined, IV_LENGTH + SALT_LENGTH, combined.length);

        SecretKeySpec keySpec = generateKeyFromPassword(password, salt);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv));

        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    private static SecretKeySpec generateKeyFromPassword(String password, byte[] salt) throws Exception {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] keyBytes = factory.generateSecret(spec).getEncoded();

        return new SecretKeySpec(keyBytes, "AES");
    }
}
