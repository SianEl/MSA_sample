package com.demo.api.system.service;

import com.demo.api.system.entity.model.AdminInfo;
import com.demo.common.model.CookieKey;
import com.demo.common.util.CookieUtils;
import com.demo.core.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final RedisService redisService;

    public void createLoginSession(AdminInfo adminInfo) {
        String adminId = adminInfo.getAdminId();
        String sessionId = UUID.randomUUID().toString();
        String sessionToken = JwtUtils.createSessionToken(adminId, sessionId);

        //redis 등록
        redisService.destoryLoginSession(adminId);
        redisService.createSession(adminInfo, sessionId, 30);
        CookieUtils.setCookie(CookieKey.SESSION_TOKEN, sessionToken, 30, true);
    }
}
