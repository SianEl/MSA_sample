package com.demo.api.system.service;

import com.demo.api.system.entity.AdminGrpMenu;
import com.demo.api.system.entity.AdminMenu;
import com.demo.api.system.repository.entity.AdminGrpMapper;
import com.demo.api.system.repository.entity.AdminMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MenuService {

    @Autowired
    private AdminMenuMapper menuMapper;

    @Autowired
    private AdminGrpMapper grpMapper;

    public List<String> getLv2MenuUrls(String id) {
        Map<Integer, AdminMenu> menuMap = getMenuMap();

        List<AdminGrpMenu> grpMenuList = getGrpMenuMap(id); // 읽기 트랜잭션 처리하기 위해 method화
        List<String> menuUrls = grpMenuList.stream()
                .map(adminGrpMenu -> menuMap.get(adminGrpMenu.getAdminMenuSeq()).getMenuUrl())
                .collect(Collectors.toList());
        return menuUrls;
    }

    private  List<AdminGrpMenu> getGrpMenuMap(String id) {
        return grpMapper.selectGrpMenuById(id);
    }

    private Map<Integer, AdminMenu> getMenuMap() {
        List<AdminMenu> menu = menuMapper.selectAll(null);
        Map<Integer, AdminMenu> menuMap = menu.stream().collect(Collectors.toMap(AdminMenu::getAdminMenuSeq, Function.identity()));
        return menuMap;
    }
}
