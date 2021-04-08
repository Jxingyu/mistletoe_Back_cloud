package com.xy.zuul.feign;

import com.xy.zuul.common.CommonResult;
import org.springframework.stereotype.Component;

@Component
public class SecurityFallBack implements SecurityFeign {
    @Override
    public CommonResult checkAccessToUri(String uri, String username) {
        return CommonResult.failed();
    }
}
