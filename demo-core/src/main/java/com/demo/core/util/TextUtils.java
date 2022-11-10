package com.demo.core.util;

import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;

public class TextUtils {
    public static String removeSpChar(String text) {
        if (!StringUtils.hasText(text)) {
            return text;
        }
        return text.replaceAll("[^가-힣ㄱ-ㅎㅏ-ㅣa-zA-Z0-9]", "");
    }

    public static String getUTF8String(String text) {
        if (!StringUtils.hasText(text)) {
            return text;
        }

        try {
            return new String(text.getBytes("UTF-8"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return text;
        }
    }

    /**
     * <pre>
     * getSplitValue(null, "_", *)      = null
     * getSplitValue("", "_", *)        = null
     * getSplitValue("_asd_ _", "_", 0) = null
     * getSplitValue("_asd_ _", "_", 1) = "asd"
     * getSplitValue("_asd_ _", "_", 2) = " "
     * getSplitValue("_asd_ _", "_", 3) = null
     * getSplitValue("_asd_ _", "_", 4) = null
     * </pre>
     *
     * @return empty to null
     */
    public static String getSplitValue(String input, String regex, int idx) {
        if (input == null || regex == null || idx < 0) {
            return null;
        }

        String[] arr = input.split(regex, -1);
        if (arr.length <= idx) {
            return null;
        }

        return StringUtils.hasText(arr[idx]) ? arr[idx] : null;
    }
}
