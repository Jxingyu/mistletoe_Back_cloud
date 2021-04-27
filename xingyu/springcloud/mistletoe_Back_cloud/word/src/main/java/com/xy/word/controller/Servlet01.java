package com.xy.word.controller;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
@WebServlet("/ser01")
public class Servlet01 extends HttpServlet {
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {

    }

    /**
     * 初始化方法
     * 创建Servlet对象（当有请求到达servlet时 调用此方法）
     * 方法可以被多次调用
     */
    @Override
    public void init() throws ServletException {
        System.out.println("servlet被调用了");
    }

    /**
     * 销毁Servlet对象 服务器自动调用（1.应用程序停止 2.Servlet长期未被调用 3.服务器被关闭）
     */
    @Override
    public void destroy() {
        System.out.println("servlet被销毁了");
    }

}
