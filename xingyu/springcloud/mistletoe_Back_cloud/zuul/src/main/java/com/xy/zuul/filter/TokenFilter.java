package com.xy.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.xy.zuul.common.CommonResult;
import com.xy.zuul.config.JwtTokenUtil;
import com.xy.zuul.feign.SecurityFeign;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

@Component
public class TokenFilter extends ZuulFilter {

    @Value("#{'${pathlist}'.split(',')}")
    private List<String> pathlist;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private SecurityFeign securityFeign;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletResponse response = requestContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        HttpServletRequest request = requestContext.getRequest();
        String uri = request.getRequestURI();
        //那些路径可以直接放行
        boolean a = pathlist.stream().anyMatch(path -> StringUtils.contains(uri, path));
        if (a) {
            return null;//放行
        }

        String authorization = request.getHeader("Authorization");
        if (StringUtils.isBlank(authorization)) {//非空判断
            requestContext.setResponseBody("未获取到token");
            requestContext.setSendZuulResponse(false);
            return null;
        }
        String token = StringUtils.substring(authorization, "bearer".length()).trim();

        // 验证权限第一种方法：本地验证(简单的认证)
//        checkLocal(token,requestContext,uri);

        // 第二种方法：远程调用验证(复杂的认证)
        checkLongRange(token, requestContext, uri);

        return null;
    }

    // 验证权限第一种方法：本地验证
    private Object checkLocal(String token, RequestContext requestContext, String uri) {
        Set<String> auths = null;
        try {
            auths = jwtTokenUtil.getAuthsFromToken(token);
        } catch (Exception e) {
            // 处理token过期
            if (e instanceof ExpiredJwtException) {
                requestContext.setResponseBody("token 过期");
                requestContext.setSendZuulResponse(false);
                return null;
            }
            e.printStackTrace();
        }
        //验证权限
        boolean b = auths.stream().anyMatch(auth -> StringUtils.equals(auth, uri));
        if (!b) {
            requestContext.setResponseBody("您没有权限");
            requestContext.setSendZuulResponse(false);
            return null;
        }
        return null;
    }

    // 第二种方法：远程调用验证(复杂的认证)
    private Object checkLongRange(String token, RequestContext requestContext, String uri) {
        try {
            boolean tokenExpired = jwtTokenUtil.isTokenExpired(token);
            if (tokenExpired) {
                requestContext.setResponseBody("token 过期");
                requestContext.setSendZuulResponse(false);
                return null;
            }
        } catch (Exception e) {
            // 处理token过期
            if (e instanceof ExpiredJwtException) {
                requestContext.setResponseBody("token 过期");
                requestContext.setSendZuulResponse(false);
                return null;
            }
            e.printStackTrace();
        }
        // 将uri和用户名传到security服务
        CommonResult commonResult = securityFeign.checkAccessToUri(uri, jwtTokenUtil.getUserNameFromToken(token));
        if (commonResult.getCode() == 200) {
            return null;
        } else if (commonResult.getCode() == 500) {
            requestContext.setResponseBody("您没有权限");
            requestContext.setSendZuulResponse(false);
            return null;
        }
        return null;
    }
}