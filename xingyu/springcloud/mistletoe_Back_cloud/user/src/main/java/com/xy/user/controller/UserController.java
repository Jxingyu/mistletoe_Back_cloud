package com.xy.user.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xy.common.CommonResult;
import com.xy.model.User;
import com.xy.user.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Vector;

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

    /**
     * 权限模块--人员列表
     * @param response
     * @param user
     * @throws IOException
     */

    @GetMapping("/findAll")
    @PreAuthorize("hasAuthority('user:list')")
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

    /**
     * 队员模块--队员列表更新
     * 需要超级管理员(系统管理员)权限
     * @param user
     * @return
     */
    @PostMapping("/player/update")
    @PreAuthorize("hasAuthority('player:update')")
    public CommonResult update(@RequestBody User user) {
        Integer integer = userService.playerUpdate(user);
        return CommonResult.success(integer, "code:200");
    }

    /**
     * 队员模块--队员编辑窗口查看
     * 队员列表编辑窗口查询
     * @param id
     * @return
     */
    @GetMapping("/player/findPlayerById")
    @PreAuthorize("hasAuthority('player:edit:read')")
    public void findPlayerById(@RequestParam Integer id,HttpServletResponse response) throws IOException {
        User user = userService.findPlayerById(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", user);
        response.setContentType("application/json;charset=utf-8");
//        String s = JSON.toJSONString(jsonObject);
        response.getWriter().write(JSON.toJSONString(jsonObject));
    }

    /**
     * 队员模块-- 查询所有队员列表
     * @param response
     * @param user
     * @throws IOException
     */
    @GetMapping("/findPlayerAll")
    @PreAuthorize("hasAuthority('player:list')")
    public void findPlayerAll(HttpServletResponse response,User user) throws IOException {
        Vector vector = userService.findPlayerAll(user);
        int totalCount = userService.findTotalCount(user);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", vector);
        jsonObject.put("totalCount", totalCount);
        response.setContentType("application/json;charset=utf-8");
//        String s = JSON.toJSONString(jsonObject);
        response.getWriter().write(JSON.toJSONString(jsonObject));
    }

}