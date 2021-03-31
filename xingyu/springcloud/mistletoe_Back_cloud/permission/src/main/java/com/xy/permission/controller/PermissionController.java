package com.xy.permission.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xy.common.CommonResult;
import com.xy.permission.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    PermissionService permissionService;

    @GetMapping("/selectPermission")
    public void selectPermission(HttpServletResponse response) throws IOException {
        List list = permissionService.selectPermission();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", list);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(jsonObject));
    }
}
