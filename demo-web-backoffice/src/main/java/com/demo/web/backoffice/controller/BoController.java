package com.demo.web.backoffice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
@Slf4j
public class BoController {
        @GetMapping("/loginForm")
    public String loginPage(Model model) {
        return "loginForm";
    }
}