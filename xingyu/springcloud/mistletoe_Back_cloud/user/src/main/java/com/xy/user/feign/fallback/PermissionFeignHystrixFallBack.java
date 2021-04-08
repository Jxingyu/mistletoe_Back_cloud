package com.xy.user.feign.fallback;

import com.xy.common.CommonResult;
import com.xy.user.feign.PermissionFeignClient;
import org.springframework.stereotype.Component;

@Component
public class PermissionFeignHystrixFallBack implements PermissionFeignClient {
    /**
     * Permission 服务挂掉的情况 返回假数据
     * @param id
     * @return
     */
    @Override
    public CommonResult findPerByRoleId(int id) {
        // 有的公司会返回一个model对象 id=-1，username="";
        return CommonResult.failed("远程调用异常,请检查相关被调用服务");
    }
}
