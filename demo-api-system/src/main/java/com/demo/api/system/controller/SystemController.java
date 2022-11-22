package com.demo.api.system.controller;

import com.demo.api.system.entity.Code;
import com.demo.api.system.entity.param.CodeParam;
import com.demo.api.system.service.CodeService;
import com.demo.common.controller.CommonController;
import com.demo.core.model.response.ListResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class SystemController extends CommonController {

    private final CodeService codeService;

    @GetMapping("/system/code/codeList")
    public ListResult<Code> getCodeList(CodeParam codeParam) {
        return codeService.getCodeList();
    }
}
