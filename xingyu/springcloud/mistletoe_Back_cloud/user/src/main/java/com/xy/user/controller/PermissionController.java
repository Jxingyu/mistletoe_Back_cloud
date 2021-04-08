package com.xy.user.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xy.common.CommonResult;
import com.xy.model.Permission;
import com.xy.user.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

@RestController
@RequestMapping("/permission")
public class PermissionController {
//TODO 不准备把模块分那么细，将在一个（如user服务）服务内存放多个controller及相关的层级--角色列表--权限分配--编辑窗口权限查询
    @Value("${server.port}")
    private String port;
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

    //test
    @GetMapping(value = {"/findPermissionByRoleId/{id}"})
    public CommonResult findPerByRoleId(@PathVariable("id") int id) {
        System.out.println(port);//打印配置文件port 区分负载均衡端口号
        Vector<Permission> vector = permissionService.findPerByRoleId(id);
        return CommonResult.success(vector, "200");
    }
}
