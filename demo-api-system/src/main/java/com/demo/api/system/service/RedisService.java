package com.demo.api.system.service;

import com.demo.api.system.entity.model.AdminInfo;
import com.demo.common.model.RedisKey;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final StringRedisTemplate stringRedisTemplate;
    private final RedisTemplate<String, Object> redisTemplate;

    public void createSession(AdminInfo adminInfo, String sessionId, long timeoutMinutes) {
        String sessionKey = RedisKey.SESSION_PREFIX + adminInfo.getAdminId();
        String sessionValue = sessionId + "@" + adminInfo.getLocalIp();
        stringRedisTemplate.opsForValue().set(sessionKey, sessionValue, 30, TimeUnit.MINUTES);

        String userInfoKey = RedisKey.SESSION_ADMIN_INFO_PREFIX + adminInfo.getAdminId();
        redisTemplate.opsForValue().set(userInfoKey, adminInfo, 30, TimeUnit.MINUTES);
    }

    public void sessionExpire(String adminId, long timeoutMinutes) {
        String sessionKey = RedisKey.SESSION_PREFIX + adminId;
        stringRedisTemplate.expire(sessionKey, timeoutMinutes, TimeUnit.MINUTES);

        String adminInfoKey = RedisKey.SESSION_ADMIN_INFO_PREFIX + adminId;
        redisTemplate.expire(adminInfoKey, timeoutMinutes, TimeUnit.MINUTES);
    }

    public String getSessionId(String userId) {
        String sessionKey = RedisKey.SESSION_PREFIX + userId;
        String sessionValue = stringRedisTemplate.opsForValue().get(sessionKey);

        if (!StringUtils.contains(sessionValue, "@")) {
            return null;
        }

        String sessionId = StringUtils.substring(sessionValue, 0, sessionValue.indexOf("@"));
        return sessionId;
    }

    public AdminInfo getSessionAdminInfo(String adminId) {
        String userInfoKey = RedisKey.SESSION_ADMIN_INFO_PREFIX + adminId;

        AdminInfo adminInfo = (AdminInfo) redisTemplate.opsForValue().get(userInfoKey);
        return adminInfo;
    }

    public void destoryLoginSession(String adminId) {
        String sessionKey = RedisKey.SESSION_PREFIX + adminId;
        stringRedisTemplate.delete(sessionKey);

        String adminInfoKey = RedisKey.SESSION_ADMIN_INFO_PREFIX + adminId;
        redisTemplate.delete(adminInfoKey);
    }


}
