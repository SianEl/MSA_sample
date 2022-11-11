package com.demo.api.system.service;

import com.demo.api.system.entity.model.AdminInfo;
import com.demo.common.model.CookieKey;
import com.demo.core.model.ErrorCode;
import com.demo.core.model.response.Result;
import com.demo.common.util.CookieUtils;
import com.demo.core.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
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

    public Result getAdminAuth(String url) {
        String sessionToken = CookieUtils.getCookieValue(CookieKey.SESSION_TOKEN);
        if(sessionToken == null) {
            return new Result(ErrorCode.LOGIN_ERROR.getValue(), ErrorCode.LOGIN_ERROR.getDescription());
        }

        String adminId = JwtUtils.getAdminId(sessionToken);
        String sessionId = JwtUtils.getSessionId(sessionToken);

        if(adminId == null || sessionId == null) {
            return new Result(ErrorCode.LOGIN_ERROR.getValue(), ErrorCode.LOGIN_ERROR.getDescription());
        }

        String registeredSessionId =redisService.getSessionId(adminId);
        AdminInfo adminInfo = redisService.getSessionAdminInfo(adminId);

        if (StringUtils.isEmpty(registeredSessionId) || adminInfo == null) {
            this.destroyLoginSession();
            return new Result(ErrorCode.LOGIN_ERROR.getValue(), ErrorCode.LOGIN_ERROR.getDescription());
        }

        // 접근권한
        List<String> accMenu = adminInfo.getAccMenu();
        /*if(accMenu.stream().noneMatch(url::contains)) {
            return new Result(ErrorCode.INACCESSIBLE.getValue(), ErrorCode.INACCESSIBLE.getDescription());
        }*/


        // 정상적으로 인증 시 redis 30분 연장
        redisService.sessionExpire(adminId, 30);
        CookieUtils.setCookie(CookieKey.SESSION_TOKEN, sessionToken, 30, true);
        return new Result();
    }

    public void destroyLoginSession() {
        // 쿠키 정보 조회
        String sessionToken = CookieUtils.getCookieValue(CookieKey.SESSION_TOKEN);

        if (StringUtils.isEmpty(sessionToken)) {
            return;
        }

        String adminId = JwtUtils.getAdminId(sessionToken);

        if (StringUtils.isEmpty(adminId)) {
            return;
        }

        // redis 삭제
        redisService.destoryLoginSession(adminId);;

        // 쿠키 삭제
        CookieUtils.removeCookie(CookieKey.SESSION_TOKEN);
        log.debug("logout, id: {}", adminId);
    }

    public AdminInfo getAdminInfo() {
        String sessionToken = CookieUtils.getCookieValue(CookieKey.SESSION_TOKEN);
        if(sessionToken == null) {
            return null;
        }

        String adminId = JwtUtils.getAdminId(sessionToken);
        String sessionId = JwtUtils.getSessionId(sessionToken);

        if(adminId == null || sessionId == null) {
            return null;
        }

        AdminInfo adminInfo = redisService.getSessionAdminInfo(adminId);

        return adminInfo;
    }
}
