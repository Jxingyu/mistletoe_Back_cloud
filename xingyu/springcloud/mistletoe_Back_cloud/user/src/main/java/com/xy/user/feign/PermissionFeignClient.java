package com.xy.user.feign;

import com.xy.common.CommonResult;
import com.xy.user.feign.fallback.PermissionFeignHystrixFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * fallback:服务调用异常的场合
 */
@FeignClient(value = "permission-service", fallback = PermissionFeignHystrixFallBack.class)//调用目标的服务名From 目标模块yml文件
public interface PermissionFeignClient {
    @GetMapping(value = {"permission/findPermissionByRoleId/{id}"})
    CommonResult findPerByRoleId(@PathVariable("id") int id);
}
