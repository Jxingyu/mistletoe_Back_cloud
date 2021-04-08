package com.xy.zuul.common;//package com.xy.security.common;
//
//import com.xy.security.config.JwtTokenUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * 该工具类用于获取前端传递的token，然后将解析token获取用户名
// */
//@Component
//public class GetUserNameByToken {
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//    @Value("${jwt.tokenHeader}")//Authorization
//    private String tokenHeader;
//    @Value("${jwt.tokenHead}")
//    private String tokenHead;//bearer
//
//    public String getUserName( HttpServletRequest request){
//        String authHeader = request.getHeader(this.tokenHeader);
//        String username=null;
//        // 判断 authHeader  不为空  并且以 bearer 开头
//        if (authHeader != null) {
//            boolean b1 = StringUtils.startsWithIgnoreCase(authHeader, this.tokenHead);
//            if (b1) {
//                //截取 bearer 后面的字符串  并且 两端去空格（获取token）
//                String authToken = authHeader.substring(this.tokenHead.length()).trim();// The part after "Bearer "
//                username = jwtTokenUtil.getUserNameFromToken(authToken);
//            }
//        }
//        return username;
//    }
//}
