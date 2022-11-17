package com.demo.web.backoffice.controller;

import com.demo.common.model.CookieKey;
import com.demo.web.backoffice.entity.model.AdminInfo;
import com.demo.web.backoffice.feign.AdminClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class BoController {

    @Autowired
    AdminClient adminClient;

    @GetMapping("/loginForm")
    public String loginPage(Model model, @RequestParam(value = "prevUrl", required = false) String prevUrl) {
        if (!StringUtils.isEmpty(prevUrl)) { //로그인 폼 이전 페이지
            model.addAttribute("prevUrl", prevUrl);
        }
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