package com.demo.web.backoffice.controller;

import com.demo.common.model.CookieKey;
import com.demo.common.util.CookieUtils;
import com.demo.common.util.WebUtils;
import com.demo.web.backoffice.entity.model.AdminInfo;
import com.demo.web.backoffice.feign.AdminClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class BoController {

    @Autowired
    AdminClient adminClient;

    @GetMapping("/loginForm")
    public String loginPage(Model model) {
        return "loginForm";
    }

    @GetMapping("/auth")
    public String auth(Model model) {
        String token = CookieUtils.getCookieValue(CookieKey.SESSION_TOKEN);
        AdminInfo adminInfo = adminClient.getAdminInfo();
        model.addAttribute("adminInfo", adminInfo);
        return "auth";
    }
}