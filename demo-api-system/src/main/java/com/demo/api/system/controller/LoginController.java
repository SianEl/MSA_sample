package com.demo.api.system.controller;

import com.demo.api.system.entity.Admin;
import com.demo.api.system.entity.model.AdminInfo;
import com.demo.api.system.repository.entity.AdminMapper;
import com.demo.api.system.service.LoginService;
import com.demo.api.system.service.MenuService;
import com.demo.common.exception.LoginException;
import com.demo.common.model.CommonMessage;
import com.demo.common.model.response.Result;
import com.demo.common.util.WebUtils;
import com.demo.core.util.HashUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final MenuService menuService;

    private final AdminMapper adminMapper;

    @GetMapping("/system/login")
    @ResponseBody
    public Result getLogin(@RequestParam(value = "id") String id, @RequestParam(value = "pw") String pw) throws NoSuchAlgorithmException {
        Admin admin = adminMapper.selectById(id);

        if(admin == null) {
            throw new LoginException(CommonMessage.CODE_UNRESISTED_ID, CommonMessage.ERRORMSG_UNRESISTED_ID);
        }

        String encPw = HashUtils.encrypt(pw);
        if(!admin.getAdminPw().equals(encPw)) {
            throw new LoginException(CommonMessage.CODE_INCORRECT_PASSWORD, CommonMessage.ERRORMSG_INCORRECT_PASSWORD);
        }

        // 접근 권한 가져오기
        List<String> accessibleMenu = menuService.getLv2MenuUrls(id);
        AdminInfo adminInfo = new AdminInfo();
        adminInfo.setAdminSeq(admin.getAdminSeq());
        adminInfo.setAdminId(admin.getAdminId());
        adminInfo.setCompSeq(admin.getCompSeq());
        adminInfo.setAccMenu(accessibleMenu);
        adminInfo.setLocalIp(WebUtils.getClientIp(WebUtils.getRequest()));
        // session
        loginService.createLoginSession(adminInfo);


        return new Result();
    }
}
