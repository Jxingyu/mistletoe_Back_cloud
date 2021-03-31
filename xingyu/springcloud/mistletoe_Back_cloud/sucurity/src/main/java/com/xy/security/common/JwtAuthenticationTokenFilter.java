package com.xy.security.common;

/**
 * 登录授权过滤器
 */

import com.xy.security.config.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 组件类
 * JWT登录授权过滤器
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {


    private static final Logger LOGGER = LoggerFactory.getLogger(com.xy.security.common.JwtAuthenticationTokenFilter.class);

    @Autowired
    private UserDetailsService userDetailsService;// 提供从数据库查出来的用户(根据用户名或者账号)查出来的实体from-- MyUserDetailsImpl.java

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHeader}")//Authorization  from -- application.yml
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;//bearer  from -- application.yml

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        //从 header  中获取 Authorization
            String authHeader = request.getHeader(this.tokenHeader);// tokenBody格式: "bearer jghrwg5thfdsfsrr0asfdagsdf" -- from -- application.yml
        // 判断 authHeader  不为空  并且以 bearer 开头
//        String servletPath = request.getServletPath();
//        if (servletPath .equals( "/login/check")){
//            chain.doFilter(request, response);
//        }
        if (authHeader != null) {
            boolean b1 = StringUtils.startsWithIgnoreCase(authHeader,this.tokenHead);// startsWithIgnoreCase -- 判断字符以什么开头 忽略大小写 // 以 tokenHead: bearer 开头
            if (b1) {
                //截取 bearer 后面的字符串  并且 两端去空格（获取token）
                String authToken = authHeader.substring(this.tokenHead.length()).trim();// The part after "Bearer " trim -- 前后去空格
                // 从Token中获取(解析)用户名
                String username = jwtTokenUtil.getUserNameFromToken(authToken);
                LOGGER.info("checking username:{}", username);
                // 用户名不为空  并且SecurityContextHolder.getContext()  存储 权限的容器中没有相关权限则继续
                boolean b = SecurityContextHolder.getContext().getAuthentication() == null;
                if (username != null && b) { // && b == true null
                    //从数据库读取用户信息 userDetails带权限
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                    //校验token
                    if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                        // 得到认证通过的令牌 -- authentication
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());// 获取权限
                        WebAuthenticationDetails details = new WebAuthenticationDetailsSource().buildDetails(request);// 得到当前浏览器的IP
                        //当前浏览器的用户IP 与Session（如果有）设置到 认证通过的令牌 -- authentication里
                        authentication.setDetails(details);
                        LOGGER.info("authenticated user:{}", username);
                        // 最终认证通过的令牌 存入本线程的安全容器   在访问接口拿到返回值后 要去主动清除 权限，避免干扰其他的线程
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        // authentication 认证通过的令牌 与 Controller层的接口权限 注解@PreAuthorize("hasAuthority('wx:brand:read')")对比用
                    }
                }
            }
        }
        chain.doFilter(request, response);
    }
     /*
        Jwt登录拦截过滤器
        1、拦截用户请求、获取请求头
        2、判断请求头是否为空并且是否是以bearer开头
        3、截取bearer后面的字符串并去掉两端空格获取token
        4、从token中获取用户名
        5、判断用户名不为空，并且SecurityContextHolder.getContext() 存储权限的容器中没有相关权限则继续
        6、根据用户名查询数据库
        7、校验token，判断用户是否登录、是否有权限以及token是否过期
        8、校验该用户拥有的权限
        9、存入用户ip (0:0:0:0:0:0:0:1)
        10、存入本线程的安全容器  在访问接口拿到返回值后 要去主动清除 权限，避免干扰其他的线程
     */
}
