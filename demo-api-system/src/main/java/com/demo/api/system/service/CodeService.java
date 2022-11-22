package com.demo.api.system.service;

import com.demo.api.system.entity.Code;
import com.demo.core.model.response.ListResult;
import org.springframework.stereotype.Service;

@Service
public class CodeService {

    public ListResult<Code> getCodeList() {
        return new ListResult<>();
    }
}
