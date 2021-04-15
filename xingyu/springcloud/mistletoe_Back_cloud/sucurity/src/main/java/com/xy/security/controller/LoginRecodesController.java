package com.xy.security.controller;

import com.xy.common.CommonResult;
import com.xy.model.LoginRecodes;
import com.xy.security.service.ILoginRecodesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Vector;

@RestController
@RequestMapping("/LoginRecodes")
public class LoginRecodesController {
    @Autowired
    private ILoginRecodesService iLoginRecodesService;

//    @PreAuthorize("hasAuthority('login:logs')")
    @GetMapping(value = {"/SelectAll"})
    public CommonResult SelectAllLoginRecodes(LoginRecodes loginRecodes) {
        Vector<LoginRecodes> vector = iLoginRecodesService.SelectAllLoginRecodes(loginRecodes);
        return CommonResult.success(vector, "200");
    }
}
