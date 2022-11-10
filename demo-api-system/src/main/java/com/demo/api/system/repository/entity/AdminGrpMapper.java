package com.demo.api.system.repository.entity;

import com.demo.api.system.entity.AdminGrpMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminGrpMapper {
    List<AdminGrpMenu> selectGrpMenuById(String id);
}
