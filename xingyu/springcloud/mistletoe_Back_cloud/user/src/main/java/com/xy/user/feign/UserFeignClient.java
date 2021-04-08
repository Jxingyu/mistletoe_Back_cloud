package com.xy.user.feign;

import com.xy.common.CommonResult;
import com.xy.model.User;
import com.xy.user.feign.fallback.PermissionFeignHystrixFallBack;
import com.xy.user.feign.fallback.UserFeignHystrixFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

@FeignClient(value = "user-service",fallback = UserFeignHystrixFallBack.class)//调用目标的服务名From 目标模块yml文件
public interface UserFeignClient {
    @GetMapping(value = {"user/findAll"})
    CommonResult findAll(User user);
}