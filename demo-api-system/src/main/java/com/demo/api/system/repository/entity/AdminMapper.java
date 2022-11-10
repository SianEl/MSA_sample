package com.demo.api.system.repository.entity;

import com.demo.api.system.entity.Admin;
import com.demo.api.system.entity.param.AdminParam;
import com.demo.api.system.repository.CrudMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper extends CrudMapper<Admin, String, AdminParam> {

}
