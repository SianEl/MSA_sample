package com.demo.api.system.entity.param;

import com.demo.api.system.entity.Admin;
import lombok.Getter;

@Getter
public class AdminParam extends Admin {
    private Integer offset;
    private Integer size;
}
