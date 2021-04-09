package com.xy.user.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xy.common.CommonResult;
import com.xy.model.Permission;
import com.xy.model.RolePermissionRelation;
import com.xy.user.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
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

    /**
     * 权限模块--权限分配窗口查看
     * 将角色信息存与权限信息存入redis(角色名字+权限信息)
     * 每小时更新一次redis中的角色与权限信息
     *
     * @param id
     * @param response
     * @throws IOException
     */
    @GetMapping("/selectPmsByRoleId")
//    @PreAuthorize("hasAuthority('permission:edit:read')")
    public void selectPmsByRoleId(@RequestParam Long id, HttpServletResponse response) throws IOException {
        ArrayList list = permissionService.selectPmsByRoleId(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", list);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(jsonObject));
    }

    /**
     * 查询当前角色rpr关联表数据
     *
     * @param id
     * @param response
     * @throws IOException
     */
    @GetMapping("/selectRpr")
    public void selectRpr(@RequestParam Long id, HttpServletResponse response) throws IOException {
        List list = permissionService.selectRpr(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", list);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(jsonObject));
    }

    /**
     * 权限模块——左边权限插入右边
     * 权限关联表 数据insert 可改为update
     *
     * @param rpr
     * @param response
     * @throws IOException
     */
    @PostMapping("/insertRolePms")
//    @PreAuthorize("hasAuthority('permission:insert')")// 权限新增
    public void insertRolePms(@RequestBody List<RolePermissionRelation> rpr, HttpServletResponse response) throws IOException {
        Integer integer = permissionService.insertRolePms(rpr);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", integer);
        //response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(jsonObject));
    }

    /**
     * 权限模块——权限分配 右边数据删除左边
     *
     * @param rpr
     * @return
     */
    @PostMapping("/deleteRpr")
//    @PreAuthorize("hasAuthority('permission:delete')")// 权限删除
    public CommonResult deleteRpr(@RequestBody List<RolePermissionRelation> rpr) {
        Integer integer = permissionService.deleteRpr(rpr);
        return CommonResult.success(integer, "delSuccess");
    }

    // test
    @GetMapping(value = {"/findPermissionByRoleId/{id}"})
    public CommonResult findPerByRoleId(@PathVariable("id") int id) {
        System.out.println(port);//打印配置文件port 区分负载均衡端口号
        Vector<Permission> vector = permissionService.findPerByRoleId(id);
        return CommonResult.success(vector, "200");
    }
}
