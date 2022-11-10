package com.demo.api.system.config;

import org.assertj.core.api.Assertions;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

public class JasyptConfigTest {

    @Test
    void jasypt() {
        String url = "jdbc:mysql://localhost:3306/system";
        String username = "root";
        String password = "9705dltka@L";

        StandardPBEStringEncryptor jasypt = new StandardPBEStringEncryptor();
        jasypt.setPassword("1q2w3e4r!");
        jasypt.setAlgorithm("PBEWithMD5AndDES");

        String encryptUrl = jasypt.encrypt(url);
        String encryptUsername = jasypt.encrypt(username);
        String encryptPassword = jasypt.encrypt(password);

        System.out.println("encryptUrl : " + encryptUrl);
        System.out.println("encryptUsername : " + encryptUsername);
        System.out.println("encryptPassword : " + encryptPassword);

        System.out.println("Url : " + jasypt.decrypt(encryptUrl));
        System.out.println("Username : " + jasypt.decrypt(encryptUsername));
        System.out.println("Password : " + jasypt.decrypt(encryptPassword));

        Assertions.assertThat(url).isEqualTo(jasypt.decrypt(encryptUrl));
    }
}
