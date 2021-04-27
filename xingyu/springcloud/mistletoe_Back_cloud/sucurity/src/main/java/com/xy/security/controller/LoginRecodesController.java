package com.xy.security.controller;

import com.xy.common.CommonResult;
import com.xy.model.LoginRecodes;
import com.xy.security.model.EsLoginInfo;
import com.xy.security.service.IESLoginInfoService;
import com.xy.security.service.ILoginRecodesService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

@RestController
@RequestMapping("/LoginRecodes")
public class LoginRecodesController {
    @Autowired
    private ILoginRecodesService iLoginRecodesService;
    @Autowired
    private IESLoginInfoService esLoginInfoService;

    //    @PreAuthorize("hasAuthority('login:logs')")
/*    @GetMapping(value = {"/SelectAll"})
    public CommonResult SelectAllLoginRecodes(LoginRecodes loginRecodes) {
        Vector<LoginRecodes> vector = iLoginRecodesService.SelectAllLoginRecodes(loginRecodes);
        return CommonResult.success(vector, "200");
    }*/
    @GetMapping(value = {"/SelectAll/{keyWord}","/SelectAll"})
    public CommonResult findLoginInfoBySearch(@PathVariable(value = "keyWord", required = false) String keyWord) throws IOException {
        List<EsLoginInfo> search = esLoginInfoService.search(keyWord);
        return CommonResult.success(search);
    }

}
