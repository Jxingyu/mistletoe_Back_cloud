package com.xy.user.controller;

import com.xy.common.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/findNameById")
    public CommonResult findNameById(@RequestParam Integer id) {
        return CommonResult.success("LOOK ME");
    }
}
