package com.demo.api.system.entity.param;

import com.demo.api.system.entity.AdminMenu;
import lombok.Getter;

@Getter
public class AdminMenuParam extends AdminMenu {
    private Integer minMenuLv;
    private Integer maxMenuLv;

    private Integer offset;
    private Integer size;
}
