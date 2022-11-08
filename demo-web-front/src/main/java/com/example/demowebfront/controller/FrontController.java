package com.example.demowebfront.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/demo-front")
@Slf4j
public class FrontController {
    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the Demo Front";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("front-request") String header) {
        System.out.println(header);
        return "Hello World in Demo Front";
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("/login");
    }


}
