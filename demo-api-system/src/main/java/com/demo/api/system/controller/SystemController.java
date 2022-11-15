package com.demo.api.system.controller;

import com.demo.api.system.entity.Code;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class SystemController {

    @GetMapping("/system/getCode")
    public List<Code> getCode() {
        return new ArrayList<>();
    }
}
