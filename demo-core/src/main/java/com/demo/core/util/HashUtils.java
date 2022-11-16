package com.demo.core.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
@Slf4j
public class HashUtils {

    /**
     * SHA-512 처리
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String sha512(String str, String salt) {
        String SHA = "";
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.reset();
            md.update(salt.getBytes("UTF-8"));

            byte byteData[] = md.digest(str.getBytes("UTF-8"));
            SHA = new String(Base64.encodeBase64(byteData), "UTF-8");
        } catch (NoSuchAlgorithmException e) {
            log.error("NoSuchAlgorithmException : {}", e.getMessage());
        } catch (UnsupportedEncodingException e) {
            log.error("UnsupportedEncodingException : {}", e.getMessage());
        } finally {
            if(StringUtils.isEmpty(SHA)){
                SHA = null;
            }
        }
        return SHA;
    }

    /**
     * SHA-512 처리
     * @param str
     * @return
     */
    public static String sha512(String str) {
        String SHA = "";
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte byteData[] = md.digest(str.getBytes("UTF-8"));
            SHA = new String(Base64.encodeBase64(byteData), "UTF-8");
        } catch (NoSuchAlgorithmException e) {
            log.error("NoSuchAlgorithmException : {}", e.getMessage());
        } catch (UnsupportedEncodingException e) {
            log.error("UnsupportedEncodingException : {}", e.getMessage());
        } finally {
            if(StringUtils.isEmpty(SHA)){
                SHA = null;
            }
        }
        return SHA;
    }
}
