package com.demo.api.system.controller;

import com.demo.api.system.entity.Admin;
import com.demo.api.system.entity.model.AdminInfo;
import com.demo.api.system.repository.entity.AdminMapper;
import com.demo.api.system.service.MenuService;
import com.demo.common.controller.CommonController;
import com.demo.common.exception.LoginException;
import com.demo.common.model.ExceptionMessage;
import com.demo.common.model.SessionKey;
import com.demo.core.model.ErrorCode;
import com.demo.core.model.response.Result;
import com.demo.core.util.HashUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LoginController extends CommonController {

    private final MenuService menuService;

    private final AdminMapper adminMapper;

    @GetMapping("/system/login")
    public Result<AdminInfo> getLogin(@RequestParam(value = "id") String id, @RequestParam(value = "pw") String pw) {
        Admin admin = adminMapper.selectById(id);

        if (admin == null) {
            throw new LoginException(ExceptionMessage.CODE_UNRESISTED_ID, ExceptionMessage.ERRORMSG_UNRESISTED_ID);
        }

        String encPw = HashUtils.sha512(pw);
        if (!admin.getAdminPw().equals(encPw)) {
            throw new LoginException(ExceptionMessage.CODE_INCORRECT_PASSWORD, ExceptionMessage.ERRORMSG_INCORRECT_PASSWORD);
        }

        // 접근 권한 가져오기
        List<String> accessibleMenu = menuService.getLv2MenuUrls(id);
        AdminInfo adminInfo = new AdminInfo();
        adminInfo.setAdminSeq(admin.getAdminSeq());
        adminInfo.setAdminId(admin.getAdminId());
        adminInfo.setCompSeq(admin.getCompSeq());
        adminInfo.setAccMenu(accessibleMenu);
        adminInfo.setLocalIp(getClientIp(getRequest()));

        HttpSession session = getRequest().getSession();

        // 갱신을 위해 삭제 후 생성
        session.removeAttribute(SessionKey.ADMIN_KEY);
        session.setAttribute(SessionKey.ADMIN_KEY, adminInfo);

        return new Result(adminInfo);
    }

    @GetMapping("/system/adminAuth")
    public Result getAdminAuth(@RequestParam("api") String api) {
        HttpSession session = getRequest().getSession();
        AdminInfo adminInfo = (AdminInfo) session.getAttribute(SessionKey.ADMIN_KEY);
        if (adminInfo == null) { // 로그인 정보가 없을 경우
            return new Result(ErrorCode.LOGIN_ERROR.getValue(), ErrorCode.LOGIN_ERROR.getDescription());
        } else if (!adminInfo.getAccMenu().contains(api)) { // api가 접근가능한 메뉴가 아닐 경우
            return new Result(ErrorCode.INACCESSIBLE.getValue(), ErrorCode.INACCESSIBLE.getDescription());
        }

        // 만료시간 갱신을 위해 삭제 후 재생성?
        session.removeAttribute(SessionKey.ADMIN_KEY);
        session.setAttribute(SessionKey.ADMIN_KEY, adminInfo);

        return new Result();
    }

    @GetMapping("/system/getLoginInfo")
    public Result<AdminInfo> getAdminInfo() {
        HttpSession session = getRequest().getSession();
        AdminInfo adminInfo = (AdminInfo) session.getAttribute(SessionKey.ADMIN_KEY);

        return new Result(adminInfo);
    }
}
