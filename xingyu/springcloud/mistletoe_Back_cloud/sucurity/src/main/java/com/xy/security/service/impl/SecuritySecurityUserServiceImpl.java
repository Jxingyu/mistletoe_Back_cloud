package com.xy.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.common.CommonResult;
import com.xy.model.Permission;
import com.xy.model.User;
import com.xy.model.UserTeamRelation;
import com.xy.security.config.JwtTokenUtil;
import com.xy.security.mapper.LoginUserMapper;
import com.xy.security.service.SecurityUserService;
import com.xy.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
 * @author xingyu
 * @since 2021-01-21
 */
@Service
public class SecuritySecurityUserServiceImpl extends ServiceImpl<LoginUserMapper, User> implements SecurityUserService {
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

//    @Override
//    /**
//     * 用户查询所有
//     */
//    public Vector findAll(User user) {
//        return loginUserMapper.findAll(user);
//    }
//
//    /**
//     * 队员列表信息更新
//     * 密码更新 单独走security自带的passwordEncoder类下的.encode方法-- 进行加密
//     * 冻结权限 走Redis判断(更新Redis冻结次数(标识))
//     *
//     * @param user
//     * @return
//     */
//    @Override
//    public Integer playerUpdate(User user) {
//        String password = user.getPassword();
//        Assert.notNull(password, "修改密码不能为空");
//        String nowEncode = passwordEncoder.encode(password);// 返回一个加密后密码
//        user.setPassword(nowEncode);
//        Integer nowStatus = user.getStatus();
//        Assert.notNull(nowStatus, "修改状态不能为空");
//        if (nowStatus == 0) { // 如果状态参数修改0 去Redis里直接冻结(登录失败次数修改为3)
//            redisService.set("LOGIN_USER_NAME:" + user.getUsername(), 3);
//        } else {// 解除冻结
//            redisService.set("LOGIN_USER_NAME:" + user.getUsername(), 0);
//        }
//        return loginUserMapper.playerUpdate(user);
//    }
//
//    /**
//     * 单文件上传(用户图片更新)
//     *
//     * @param user
//     * @return
//     */
//    @Override
//    public Integer uploadFile(User user) {
//        return loginUserMapper.uploadFile(user);
//    }
//
//    /**
//     * 用户列表编辑窗口查询
//     *
//     * @param id
//     * @return
//     */
//    @Override
//    public User findPlayerById(Integer id) {
//        return loginUserMapper.findPlayerById(id);
//    }
//
//    @Override
//    public int findTotalCount(User user) {
//        return loginUserMapper.findTotalCount(user);
//    }
//
//    /**
//     * 队员查询所有
//     *
//     * @param user
//     * @return
//     */
//    @Override
//    public Vector findPlayerAll(User user) {
//        return loginUserMapper.findPlayerAll(user);
//    }
//
//    /**
//     * 注册
//     * 暂无角色
//     *
//     * @param user
//     * @return
//     */
//    @Override
//    public CommonResult register(User user) {
//        String encode = passwordEncoder.encode(user.getPassword());
//        user.setPassword(encode);
////        user.setRole("暂无角色");
//        loginUserMapper.register(user);
//        // 查询最大Id最新Id用来更新 去user_role_relation表 去赋一个默认角色(暂无角色)
//        final Integer maxId = loginUserMapper.getMaxId();
//        HashMap map = new HashMap();
//        map.put("NewID", maxId);
//        loginUserMapper.urrInsert(maxId);
//        return CommonResult.success(map, "NewID");
//    }
//
//    @Override
//    public Integer registerIconUpdate(User user) {
//        return loginUserMapper.registerIconUpdate(user);
//    }
//
//    @Override
//    public List<UserTeamRelation> findUserByTeamNumbers(Integer numbers) {
//        return loginUserMapper.findUserByTeamNumbers(numbers);
//    }
//
//    @Override
//    public String selectTeamNameByNumbers(Integer numbers) {
//        return loginUserMapper.selectTeamNameByNumbers(numbers);
//    }
//
//    @Override
//    public String selectUsernameByUserId(Integer userId) {
//        return loginUserMapper.selectUsernameByUserId(userId);
//    }

}
