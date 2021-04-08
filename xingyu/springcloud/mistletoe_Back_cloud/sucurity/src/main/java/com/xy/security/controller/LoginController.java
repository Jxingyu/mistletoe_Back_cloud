package com.xy.security.controller;

import com.alibaba.fastjson.JSONObject;
import com.xy.common.CommonResult;
import com.xy.security.model.User;
import com.xy.security.service.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * <p>
 * 后台用户登录 前端控制器
 * </p>
 *
 * @author xingyu
 * @since 2021-01-21
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private SecurityUserService securityUserService;

    @PostMapping("/check")
    public void login(@RequestBody User user, HttpSession session, HttpServletRequest request, HttpServletResponse resp) throws IOException {
        System.out.println(user);
        CommonResult result = (CommonResult) securityUserService.login(user, request);
        String utilCode = (String) session.getAttribute("code");//拿到后台生成的验证码与用户传入model的验证码对比用
//            if (utilCode.equalsIgnoreCase(user.getCode())) {// equalsIgnoreCase 不区分大小写对比

        if (utilCode.equals(user.getCaptcha())) {

            if (result.getMessage().equals("TokenSuccess")) {
                long tokenMap = result.getCode(); // 打印Token
                System.out.println(tokenMap);
                // 5.得到返回结果
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code", tokenMap);
                jsonObject.put("message", result.getMessage());
                jsonObject.put("result", result.getData());
                resp.getWriter().println(jsonObject);
            }// 冻结场合
            if (result.getMessage().equals("Account has been frozen 24 h")) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("result", result.getMessage());
                resp.getWriter().println(jsonObject);
            }// 未冻结状态下 登录账号或者密码错误场合
            if (result.getMessage().equals("failed")) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("result", result.getMessage());
                resp.getWriter().println(jsonObject);
            }
        } else {// 验证码错误场合
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", "CaptchaError");
            resp.getWriter().println(jsonObject);
            return;
        }
//        return userService.login(user);
    }

    /**
     * e
     * 远程权限验证
     *
     * @param uri
     * @param username
     * @return
     */
    @GetMapping("/checkAccessToUri")
    public CommonResult checkAccessToUri(@RequestParam String uri, @RequestParam String username) {
        return securityUserService.checkAccessToUri(uri, username);
    }
}
