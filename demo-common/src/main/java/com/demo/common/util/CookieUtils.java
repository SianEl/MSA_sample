package com.demo.common.util;

import javax.servlet.http.Cookie;
import java.util.concurrent.TimeUnit;

public class CookieUtils {
    public static void setCookie(String key, String value) {
        setCookie(key, value, -1, null, false);
    }

    public static void setCookie(String key, String value, boolean httpOnly) {
        setCookie(key, value, -1, null, httpOnly);
    }

    public static void setCookie(String key, String value, int expireMinutes) {
        setCookie(key, value, expireMinutes, null, false);
    }

    public static void setCookie(String key, String value, int expireMinutes, boolean httpOnly) {
        setCookie(key, value, expireMinutes, null, httpOnly);
    }

    public static void setCookie(String key, String value, int expireMinutes, String domain, boolean httpOnly) {
        Cookie cookie = new Cookie(key, XssUtils.filter(value));
        cookie.setPath("/");
        cookie.setMaxAge((int) TimeUnit.MINUTES.toSeconds(expireMinutes));
        cookie.setHttpOnly(httpOnly);
        if (domain != null) {
            cookie.setDomain(domain);
        }

        WebUtils.getResponse().addCookie(cookie);
    }
}
