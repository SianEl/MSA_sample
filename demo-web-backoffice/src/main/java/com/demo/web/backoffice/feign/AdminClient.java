package com.demo.web.backoffice.feign;

import com.demo.web.backoffice.entity.model.AdminInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="demo-api-system")
public interface AdminClient {
    @GetMapping("/system/getLoginInfo")
    AdminInfo getAdminInfo();
}
