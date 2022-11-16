package com.demo.gateway.config;

import org.assertj.core.api.Assertions;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;

public class JasyptConfigTest {

    @Test
    void jasypt() {

        String accessValue = "1q2w3e4r!";

        StandardPBEStringEncryptor jasypt = new StandardPBEStringEncryptor();
        jasypt.setPassword("1q2w3e4r!");
        jasypt.setAlgorithm("PBEWithMD5AndDES");


        String encryptValue = jasypt.encrypt(accessValue);


        System.out.println("encryptValue : " + encryptValue);

        System.out.println("accessValue : " + jasypt.decrypt(encryptValue));

        Assertions.assertThat(accessValue).isEqualTo(jasypt.decrypt(encryptValue));
    }
}
