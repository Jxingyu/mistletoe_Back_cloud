package com.xy.user.feign.fallback;

import com.xy.common.CommonResult;
import com.xy.model.User;
import com.xy.user.feign.PermissionFeignClient;
import com.xy.user.feign.UserFeignClient;
import org.springframework.stereotype.Component;

@Component
public class UserFeignHystrixFallBack  implements UserFeignClient {
    /**
     * Permission 服务挂掉的情况 返回假数据
     * @param user
     * @return
     */
    @Override
    public CommonResult findAll(User user) {
        return CommonResult.failed("远程调用异常,请检查相关被调用服务");
    }
}