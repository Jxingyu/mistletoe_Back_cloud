package com.xy.user.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xy.common.CommonResult;
import com.xy.common.MyImgLoad;
import com.xy.model.Role;
import com.xy.model.User;
import com.xy.user.feign.PermissionFeignClient;
import com.xy.user.feign.UserFeignClient;
import com.xy.user.service.RoleService;
import com.xy.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 * 后台用户表 前端控制器
 * </p>
 *
 * @author xingyu
 * @since 2021-01-21
 */

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

    @Autowired
//    这里用于此user模块 调取其他模块的接口 ribbon
    private RestTemplate restTemplate;//Java里发问网络请求 调接口用;    类似于：阿帕奇 HttpClient || Android 开发用的 OkHttp

    @Autowired
    PermissionFeignClient permissionFeignClient;

    @Autowired
    UserFeignClient userFeignClient;


    /**
     * 权限模块--人员列表
     *
     * @param response
     * @param user
     * @throws IOException
     */

    @GetMapping("/findAll")
//    @PreAuthorize("hasAuthority('user:list')")
    public void findAll(HttpServletResponse response, User user) throws IOException {
        Vector vector = userService.findAll(user);
//        int totalCount = userService.findTotalCount(user);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", vector);
//        jsonObject.put("totalCount", totalCount);
        response.setContentType("application/json;charset=utf-8");
//        String s = JSON.toJSONString(jsonObject);
        response.getWriter().write(JSON.toJSONString(jsonObject));
    }
//    @GetMapping(value = {"/findAll"})
//    public CommonResult findAllFeign(User user) throws IOException {
//        CommonResult commonResult = userFeignClient.findAll(user);
//        if (commonResult.getCode() == 500) {
//            return commonResult;
//        }
//        Object data = commonResult.getData();
//        return CommonResult.success(data);
//    }

    /**
     * 查询权限模块 角色分配按钮下 查询所有角色（放行）
     *
     * @param id
     * @param response
     * @throws IOException
     */
    @GetMapping("/findRoleName")
    public void findRoleName(@RequestParam Integer id, HttpServletResponse response) throws IOException {
        List vector = roleService.findRoleNameById(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", vector);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(jsonObject));
    }

    /**
     * 权限模块--人员列表--角色分配按钮 用户当前所属角色 编辑窗口查看
     *
     * @param id
     * @param response
     * @throws IOException
     */
    @GetMapping("/findNameByUserId")
    public void findNowRoleById(@RequestParam Integer id, HttpServletResponse response) throws IOException {
        List vector = roleService.findNowRoleByUserId(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("findNameByUserId", vector);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(jsonObject));
    }

    /**
     * 权限模块 穿梭框左边数据移至右边（分配角色）
     *
     * @param role
     * @param response
     * @throws IOException
     */
    @PostMapping("/updateUserRole")
    public void updateUserRole(@RequestBody Role role, HttpServletResponse response) throws IOException {
        Integer tat = roleService.updateUserRole(role);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", tat);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(jsonObject));
    }

    /**
     * 权限模块 穿梭框 右边数据移至左边(移除角色)(多余功能 放行)
     *
     * @param role
     * @param response
     * @throws IOException
     */
    @PostMapping("/updateUserRoleTwo")
    public void updateUserRoleTwo(@RequestBody Role role, HttpServletResponse response) throws IOException {
        Integer tat = roleService.updateUserRoleTwo(role);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", tat);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(jsonObject));
    }

    @GetMapping("/role/findAll")
    public void findAll(HttpServletResponse response) throws IOException {
        List<Role> RoleSelectAllVector = roleService.findAll();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", RoleSelectAllVector);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(jsonObject));
//        return CommonResult.success(RoleSelectAllVector,"success");
    }


    /**
     * 队员模块--队员列表更新
     * 需要超级管理员(系统管理员)权限
     *
     * @param user
     * @return
     */
    @PostMapping("/player/update")
//    @PreAuthorize("hasAuthority('player:update')")
    public CommonResult update(@RequestBody User user) {
        Integer integer = userService.playerUpdate(user);
        return CommonResult.success(integer, "code:200");
    }

    /**
     * 队员模块--队员编辑窗口查看
     * 队员列表编辑窗口查询
     *
     * @param id
     * @return
     */
    @GetMapping("/player/findPlayerById")
//    @PreAuthorize("hasAuthority('player:edit:read')")
    public void findPlayerById(@RequestParam Integer id, HttpServletResponse response) throws IOException {
        User user = userService.findPlayerById(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", user);
        response.setContentType("application/json;charset=utf-8");
//        String s = JSON.toJSONString(jsonObject);
        response.getWriter().write(JSON.toJSONString(jsonObject));
    }

    /**
     * 队员模块-- 查询所有队员列表
     *
     * @param response
     * @param user
     * @throws IOException
     */
    @GetMapping("/findPlayerAll")
//    @PreAuthorize("hasAuthority('player:list')")
    public void findPlayerAll(HttpServletResponse response, User user) throws IOException {
        Vector vector = userService.findPlayerAll(user);
        int totalCount = userService.findTotalCount(user);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", vector);
        jsonObject.put("totalCount", totalCount);
        response.setContentType("application/json;charset=utf-8");
//        String s = JSON.toJSONString(jsonObject);
        response.getWriter().write(JSON.toJSONString(jsonObject));
    }


    private static String imgurl = "/images/";

    /**
     * 队员个人信息 图片编辑功能
     *
     * @param id
     * @param request
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/player/iconUpdate")
    public CommonResult uploadFile(@RequestParam int id, HttpServletRequest request, @RequestParam("icon") MultipartFile file) throws IOException {
        MyImgLoad.loadOne(request, file);
        User user = new User();
        user.setId(id);
        user.setIcon((String) request.getAttribute("imgHref"));
        Integer integer = userService.uploadFile(user);
        return CommonResult.success(integer, "code:200");
    }

    /**
     * 注册页面图片上传功能
     *
     * @param id
     * @param request
     * @param file
     * @return
     */
    @PostMapping("/registerIconUpdate")
    public CommonResult registerIconUpdate(@RequestParam int id, HttpServletRequest request, @RequestParam("icon") MultipartFile file) {
        MyImgLoad.loadOne(request, file);
        User user = new User();
        user.setId(id);
        user.setIcon((String) request.getAttribute("imgHref"));
        Integer integer = userService.registerIconUpdate(user);
        return CommonResult.success(integer, "code:200");
    }

    /**
     * 个人信息注册
     *
     * @param user
     * @return
     */
    @PostMapping("/register/check")
    public CommonResult register(@RequestBody User user) {
        CommonResult result = userService.register(user);
        return CommonResult.success(result, "200");
    }


    /**
     * Test
     * 基于feign实现远程调用
     *
     * @param id
     */
    //test远程调用Permission服务
    // 将查询出来的userId远程传递到Permission服务 获取角色  获取权限
    @GetMapping(value = {"/findPermissionByRoleId/{id}"})
    public CommonResult findPreById(@PathVariable("id") Integer id) {
        CommonResult permissionFeignResult = permissionFeignClient.findPerByRoleId(id);
        if (permissionFeignResult.getCode() == 500) {
            return permissionFeignResult;
        }
        Object data = permissionFeignResult.getData();
        return CommonResult.success(data);
    }
    /**
     * Test
     * 基于ribbon实现远程调用
     *
     * @param id
     * @return
     */
    //test远程调用Permission服务
    // 将查询出来的userId远程传递到Permission服务 获取角色  获取权限
//    @GetMapping(value = {"/findPermissionByRoleId/{id}"})
//    public CommonResult findPlayerById(@PathVariable("id") Integer id) {
////        CommonResult forObject = restTemplate.getForObject("http://localhost:8110/permission/findPermissionByRoleId/" + id, CommonResult.class);
//        //因为负载均衡 所有IP和端口不能写死 要改为模块的服务名
//        CommonResult forObject = restTemplate.getForObject("http://permission-service/permission/findPermissionByRoleId/" + id, CommonResult.class);
//        // 获取permission权限ByRoleId
//        List data = (List) forObject.getData();
//        return CommonResult.success(data, "200");
//    }
}