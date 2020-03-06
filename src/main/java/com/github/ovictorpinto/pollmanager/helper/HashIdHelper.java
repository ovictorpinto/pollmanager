package com.github.ovictorpinto.pollmanager.helper;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;

@Component
public class HashIdHelper {

    private final String key;

    /**
     * randomly generated key
     */
    public HashIdHelper(){
        this.key = "6WkHxYjwM-TyEf3z8T_nh7opeW";
    }

    /**
     * Convert a id to a enconded hash to use as parameter.
     * @param id
     * @return
     * @throws Exception
     */
    public String encrypt(Long id) throws Exception {
        byte[] encryptKey = key.getBytes(StandardCharsets.UTF_8);
        Cipher cipher = Cipher.getInstance("DESede");
        KeySpec keySpec = new DESedeKeySpec(encryptKey);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DESede");
        SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
        cipher.init(1, secretKey);
        String value = String.valueOf(id);
        byte[] cipherText = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
        byte[] cripted = Base64.getEncoder().encodeToString(cipherText).getBytes(StandardCharsets.UTF_8);
        String urlable = Base64.getEncoder().encodeToString(cripted);
        return urlable;
    }

    /**
     * Make inverse operation of {@link HashIdHelper}#encript
     * @param valueCripted
     * @return
     * @throws Exception
     */
    public long decrypt(String valueCripted) throws Exception {
        String value = new String(Base64.getDecoder().decode(valueCripted));
        byte[] encryptKey = key.getBytes(StandardCharsets.UTF_8);
        Cipher cipher = Cipher.getInstance("DESede");
        KeySpec keySpec = new DESedeKeySpec(encryptKey);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DESede");
        SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
        cipher.init(2, secretKey);
        byte[] decipherText = cipher.doFinal(Base64.getDecoder().decode(value));
        return Long.parseLong(new String(decipherText));
    }
}
