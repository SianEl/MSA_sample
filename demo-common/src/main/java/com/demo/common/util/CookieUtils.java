package com.demo.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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

    public static String getCookieValue(String key) {
        return getCookieValue(key, WebUtils.getRequest());
    }

    public static String getCookieValue(String key, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(key)) {
                return XssUtils.filter(cookie.getValue());
            }
        }

        return null;
    }

    public static void removeCookie(String key) {
        Cookie cookie = new Cookie(key, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        WebUtils.getResponse().addCookie(cookie);
    }
}
