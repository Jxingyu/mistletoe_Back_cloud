package com.xy.security.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xy.common.DateUtils;
import com.xy.model.LoginRecodes;
import com.xy.security.model.User;
import com.xy.security.mapper.LoginUserMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Ip获取
 */
//.使用aop技术记录每次用户登陆的:时间、ip、用户id等信息存入elasticsearch
//.生成用户登录信息表，用户自己可查看所有登录ip地址，登录时间等信息
@Aspect// 切面类
@Component
// @Component （把普通pojo实例化到spring容器中，相当于配置文件中的 ）
//泛指各种组件，就是说当我们的类不属于各种归类的时候（不属于@Controller、@Services等的时候），我们就可以使用@Component来标注这个类
public class IpGetAdvice {
    @Autowired
    private LoginUserMapper loginUserMapper;

    private Logger logger = LoggerFactory.getLogger(IpGetAdvice.class);

    // 切点定义 并且设置切点作用域
    @Pointcut(value = "execution( * com.xy.security.controller.LoginController.login(..))")
// controller下所有的类* 所有的方法* 不限定参数类型. 和个数.
    public void myIpPointcut() {

    }

    // 通知 （环绕通知 最强）
    @Around("myIpPointcut()")
    public Object myIp(ProceedingJoinPoint pjp) throws Throwable {

        // 获取此通知方法下的增强代码 相关类名 方法名 参数
//        String className = pjp.getTarget().getClass().toString();// 类名
//        String methodName = pjp.getSignature().getName();// 方法名
//        Object[] array = pjp.getArgs();// 参数
        List parameterList = Arrays.asList(pjp.getArgs());// 参数
        // 数组转换为字符串
        ObjectMapper mapper = new ObjectMapper();
//        logger.info("调用前类名:" + className + "方法名:" + methodName + "参数:" + mapper.writeValueAsString(array));
        Object proceed = pjp.proceed();// 这是切面中环绕通知的一个方法 pjp.proceed()方法个人理解为是一个对业务方法的模拟，可是在这个方法 前后 插入想做的事情。
//        logger.info("调用后类名:" + className + "方法名:" + methodName + "返回值:" + mapper.writeValueAsString(proceed));
        InetAddress netAddress = InetAddress.getLocalHost();
        String pcIp = netAddress.getHostAddress();
        String pcName = netAddress.getHostName();
        List list = Collections.singletonList(parameterList.get(0));

        List<User> list1 = list;
//        System.out.println(list1);
        for (int i = 0; i < list1.size(); i++) {
//            Object[] a = (Object[]) list1.get(i);
            User a = list1.get(i);
            String username1 = a.getUsername();
//            String pid = a[1].toString();
//            String name = a[2].toString();
            LoginRecodes loginRecodes = new LoginRecodes();
            loginRecodes.setUserName(username1);
            String userName = loginRecodes.getUserName();
            String loginDate = DateUtils.getCurrentDay();
            loginUserMapper.insertLoginRecords(userName,pcName,pcIp,loginDate);// 插入登录记录相关信息
        }

//        userMapper.insertLoginRecords(map);

        // 其实使用InetAddress.getLocalHost()获取ip存在问题，因为有的电脑有多个网卡，也就有多个ip地址，正确的方法应该是这样
   /*     List<String> result = new ArrayList<String>();
        Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
        InetAddress ip;
        while (netInterfaces.hasMoreElements()) {
            NetworkInterface ni = netInterfaces.nextElement();
            Enumeration<InetAddress> addresses = ni.getInetAddresses();
            while (addresses.hasMoreElements()) {
                ip = addresses.nextElement();
                if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(':') == -1) {
                    result.add(ip.getHostAddress());
                }
            }
        }
        for (String string : result) {
            System.out.println(string);
        }*/
        return proceed;
    }
}
