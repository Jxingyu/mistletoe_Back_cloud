package com.xy.zuul.filter;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.xy.zuul.common.CommonResult;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class MyLogFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {// 返回true下面run方法执行 false不执行
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String requestURI = request.getRequestURI();
        return requestURI.startsWith("/api/u");
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String remoteAddr = request.getRemoteAddr();
        String remoteHost = request.getRemoteHost();
        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/api/u")) {
            //currentContext.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
            String method = request.getMethod();//方法额类型
            System.out.println(remoteAddr + "," + remoteHost + "访问了" + requestURI + "方法");
            // 阻止请求继续前进 类似拦截器返回false
            currentContext.setSendZuulResponse(false);// (没有权限)这样写
            currentContext.setResponseBody(JSONObject.toJSONString(CommonResult.forbidden(requestURI)));// 返回无权限消息
        }
        return null;
    }
}
