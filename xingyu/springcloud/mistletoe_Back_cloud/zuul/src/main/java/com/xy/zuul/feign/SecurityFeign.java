package com.xy.zuul.feign;

import com.xy.zuul.common.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 实时查询 管理员增加的权限 立马可以使用 不用重新登录
 */
@FeignClient(value = "security-service",fallback = SecurityFallBack.class)
public interface SecurityFeign {

    @GetMapping("/login/checkAccessToUri")
    CommonResult checkAccessToUri(@RequestParam("uri") String uri, @RequestParam("username") String username);
}