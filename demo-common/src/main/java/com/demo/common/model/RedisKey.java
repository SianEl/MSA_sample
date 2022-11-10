package com.demo.common.model;

public interface RedisKey {
    /**
     * 세션 관련
     */
    public static final String SESSION_PREFIX = "demo:bo:session:";
    public static final String SESSION_ADMIN_INFO_PREFIX = "demo:bo:session:adminInfo:";
}
