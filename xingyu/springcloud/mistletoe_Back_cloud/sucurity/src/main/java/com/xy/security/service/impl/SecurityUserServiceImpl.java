package com.xy.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.common.CommonResult;
import com.xy.security.model.Permission;
import com.xy.security.model.User;
import com.xy.security.config.JwtTokenUtil;
import com.xy.security.mapper.LoginUserMapper;
import com.xy.security.service.SecurityUserService;
import com.xy.service.RedisService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author xingyu
 * @since 2021-01-21
 */
@Service
public class SecurityUserServiceImpl extends ServiceImpl<LoginUserMapper, User> implements SecurityUserService {
    @Autowired
    private LoginUserMapper loginUserMapper;

//    @Autowired
//    private UserRoleRelationMapper userRoleRelationMapper;


    /**
     * 返回一个加密后的密码
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    /**
     * JWT(Json Web Token)登录支持 用于产生token和解析token
     */
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    /**
     * 注入基于原生redisTemplate
     * 而封装在redisServiceImpl里的redisTemplate
     * 接口: RedisService
     */
    private RedisService redisService;

    /**
     * rmq
     */
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
    /**
     * 注入日志用交换机
     */
//    @Autowired
//    private DirectExchange logExchange;
    /**
     * 注入绑定 获取路由键用
     */
//    @Autowired
//    Binding LoginLogQueueToLogExchange;

    @Value("${jwt.tokenHeader}")//Authorization  from -- application.yml
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;//bearer  from -- application.yml


    /**
     * @param
     * @return
     * @Cacheable会先判断缓存是否有值 优则返回 无则去数据库
     * <p>
     * (实现一小时内用户登陆密码错误！时 错误三次以上冻结24小时的功能)
     */

    @Override
    public User getUserByUsername(String username) {
        return loginUserMapper.getUserByUsername(username);
    }


    @Override
//    @Cacheable(key = "#user")// 变量 引用下面的形参id // 使用 springCache的注解实现缓存
    public CommonResult login(User loginParams, HttpServletRequest request) {
        String username = loginParams.getUsername();
        Assert.notNull(username, "登录账号不能为空");
        String password = loginParams.getPassword();
        Assert.notNull(password, "登录密码不能为空");
        // 登陆前先去Redis判断目前登录用户是否已被冻结
        if (redisService.hasKey("LOGIN_USER_NAME:" + loginParams.getUsername())) {
            int LoginCount = (int) redisService.get("LOGIN_USER_NAME:" + loginParams.getUsername());
            if (LoginCount >= 3) {
                return CommonResult.success(LoginCount, "Account has been frozen 24 h");// 账户已被冻结
            }
        } else {
            redisService.set("LOGIN_USER_NAME:" + loginParams.getUsername(), 0);// 如果不存在 创建 防止redis 报异常
        }
        // 查询当前登录用户 数据(登陆用户的数据和数据库账号和密码是否匹配)
        User nowUser = getUserByUsername(username);
        // 对比传入的密码和数据库查出来的密码 返回一个布尔类型
        boolean matches = passwordEncoder.matches(loginParams.getPassword(), nowUser.getPassword());
        if (!matches) {// 如果不匹配 ↓
            int count = (int) redisService.get("LOGIN_USER_NAME:" + loginParams.getUsername());
            redisService.set("LOGIN_USER_NAME:" + loginParams.getUsername(), count + 1, 3600L);// 记录登录失败第一次 一小时内用户登陆失败三次以上冻结24小时的功能记录 每次登录失败+1
            //每次登录对当前账户的登录次数 做缓存 登录60分钟内 登录错误次数超过3次，账户锁定24小时
            // 等于3 时代表开始冻结账户，设置过期时间为24小时，并且对 value+1
            if (count >= 2) {
                redisService.set("LOGIN_USER_NAME:" + loginParams.getUsername(), count + 1, 3600L * 24);
                return CommonResult.success(loginParams.getUsername(), "Account has been frozen 24 h");
            }
            return CommonResult.failed("failed");
        } else {// 登录成功
            redisService.set("LOGIN_USER_NAME:" + loginParams.getUsername(), 0);// 登录成功后 将Redis 登录失败次数设为0
            int countNow = (int) redisService.get("LOGIN_USER_NAME:" + loginParams.getUsername());// countNow 更新后的实时 count
            if ((countNow == 0) || countNow <= 3) {

                /*如果密码匹配成功并且未冻结 生成Token*/
                String generateTokenOne = jwtTokenUtil.generateToken(loginParams);// 传入查出的用户数据 用于 生成返回Token
                HashMap map = new HashMap();
                map.put("bearer", tokenHead);
                map.put("Authorization", tokenHeader);
                map.put("generaToken", generateTokenOne);
                HashMap<Object, Object> loginLogMap = new HashMap<>();
                loginLogMap.put("username", username);
//                String remoteAddr = request.getRemoteAddr();//获取IP RabbitMq用
//                loginLogMap.put("ip", remoteAddr);
//                String loginLog = JSON.toJSONString(loginLogMap);//转为JSON RabbitMq用
//                rabbitTemplate.convertAndSend(logExchange.getName(), LoginLogQueueToLogExchange.getRoutingKey(), loginLog);
                return CommonResult.success(map, "TokenSuccess");
            }
        }
        return CommonResult.success("failed");
    }


    @Override
    public List<Permission> getPermissionByRoleId(Integer id) {
        return loginUserMapper.getPermissionByRoleId(id);
    }

    @Override
    /**
     * uri 传递过来的权限
     */
    public CommonResult checkAccessToUri(String uri, String username) {
        User user = getUserByUsername(username);  // 根据用户查询用户实例
        List<Permission> permissions = getPermissionByRoleId(user.getId());

        boolean b1 = permissions.stream().anyMatch(Permission -> StringUtils.equals(Permission.getUri(), uri));// 普通 uri 权限对比
        if (b1) {
            return CommonResult.success(permissions);
        } else {
            boolean b = permissions.stream().anyMatch(Permission -> uriCompared(Permission.getUri(), uri));// restFull uri 权限对比
//        return b ? CommonResult.success(true) : CommonResult.failed();
//         return b ? CommonResult.success(permissions) : CommonResult.failed("权限对比失败");
            if (b) {
                return CommonResult.success(permissions);
            } else {
                return CommonResult.failed("权限对比失败");
            }
        }

    }

    public Boolean uriCompared(String PerSqlUri, String uri) {
        PathMatcher pathMatcher = new AntPathMatcher();
//        boolean matches = pathMatcher.match("/api/u/team/deleteTeam/**", "/api/u/team/deleteTeam/100");
        boolean matches = pathMatcher.match(PerSqlUri, uri);
        return matches;
    }
}