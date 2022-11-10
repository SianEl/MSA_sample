package com.demo.common.util;

import com.nhncorp.lucy.security.xss.XssFilter;
import com.nhncorp.lucy.security.xss.XssPreventer;

public class XssUtils {
    /**
     * tag 전체 escape
     */
    public static String preventer(String dirty) {
        if (dirty == null) {
            return null;
        }

        return XssPreventer.escape(dirty);
    }

    /**
     * html tag 제외 및 lucy-xss-superset.xml 설정된 tag들만 escape
     */
    public static String filter(String dirty) {
        if (dirty == null) {
            return null;
        }

        XssFilter filter = XssFilter.getInstance("lucy-xss-superset.xml", true);
        return filter.doFilter(dirty);
    }
}
