package com.example.demowebbackofiice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo-backoffice")
public class BoController {
    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome to the Demo BackOffice";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("backoffice-request") String header) {
        System.out.println(header);
        return "Hello World in Demo BackOffice";
    }
}