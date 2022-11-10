package com.demo.api.system.repository.entity;

import com.demo.api.system.entity.AdminGrpMenu;
import com.demo.api.system.repository.CrudMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMenuMapper extends CrudMapper {

    List<AdminGrpMenu> selectGrpMenuById(String id);
}
