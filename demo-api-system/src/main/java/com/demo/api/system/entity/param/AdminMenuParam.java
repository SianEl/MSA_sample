package com.demo.api.system.entity.param;

import com.demo.api.system.entity.AdminMenu;
import lombok.Getter;

@Getter
public class AdminMenuParam extends PageParam {
    private Integer minMenuLv;
    private Integer maxMenuLv;

}
