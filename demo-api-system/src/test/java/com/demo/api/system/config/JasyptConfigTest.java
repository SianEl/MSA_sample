package com.demo.api.system.config;

import org.assertj.core.api.Assertions;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;

public class JasyptConfigTest {

    @Test
    void jasypt() {
        String url = "jdbc:mysql://localhost:3306/system";
        String username = "demo";
        String password = "9705dltka@L";

        StandardPBEStringEncryptor jasypt = new StandardPBEStringEncryptor();
        jasypt.setPassword("1q2w3e4r!");

        String encryptUrl = jasypt.encrypt(url);
        String encryptUsername = jasypt.encrypt(username);
        String encryptPassword = jasypt.encrypt(password);

        System.out.println("encryptUrl : " + encryptUrl);
        System.out.println("encryptUsername : " + encryptUsername);
        System.out.println("encryptPassword : " + encryptPassword);

        Assertions.assertThat(url).isEqualTo(jasypt.decrypt(encryptUrl));
    }
}
