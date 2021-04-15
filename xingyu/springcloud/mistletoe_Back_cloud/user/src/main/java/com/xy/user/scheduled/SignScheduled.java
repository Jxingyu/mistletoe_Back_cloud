package com.xy.user.scheduled;

import com.xy.model.Sign;
import com.xy.model.Team;
import com.xy.model.UserTeamRelation;
import com.xy.service.impl.RedisServiceImpl;
import com.xy.user.service.ISignService;
import com.xy.user.service.TeamService;
import com.xy.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component//@Component （把普通pojo实例化到spring容器中，相当于配置文件中的）
//泛指各种组件，就是说当我们的类不属于各种归类的时候（不属于@Controller、@Services等的时候），我们就可以使用@Component来标注这个类。
public class SignScheduled {
    @Autowired
    RedisServiceImpl redisService;

    @Autowired
    ISignService signService;

    @Autowired
    TeamService teamService;

    @Autowired
    UserService userService;


        @Scheduled(cron = "0 0 6 ? * 1,2,3,4,5")// 每天6点查询出今天要点到的队员，存入Redis做为今天要点到的队员表单
//    @Scheduled(cron = "*/30 * * * * ?")// 每隔30秒执行一次：
    public void creatSign() {
        List<Team> allTeam = teamService.findAllTeam();// 查询所有团队
        allTeam.stream().forEach(team -> {
            List<UserTeamRelation> list = userService.findUserByTeamNumbers(team.getNumbers());
            List<Sign> signs = new ArrayList<>();
            for (UserTeamRelation utr : list) {
                Sign sign = new Sign();
                sign.setTeamName(userService.selectTeamNameByNumbers(utr.getNumbers()));
                sign.setUserId(utr.getUserId());
                sign.setUsername(userService.selectUsernameByUserId(utr.getUserId()));
                sign.setSignTime(sign.getSignTime());
                sign.setSign("");
                signs.add(sign);
            }
            redisService.set("RBAC_SYSTEM:SIGN:TODAY_TEAM" + team.getNumbers(), signs);
        });
    }

        @Scheduled(cron = "0 0 10 ? * 1,2,3,4,5")//每天早上6点 生成表后 到10点前 都可以点到, 最终10点去提交到数据库,并且删除当天redis里的点到记录
//    @Scheduled(cron = "0 */1 * * * ?")// 测试 每1分钟提交表到数据库
    public void submmit() {
        List<Sign> signList = new ArrayList<>();
        Set<String> signSet = redisService.patternKeys("RBAC_SYSTEM:SIGN:TODAY_TEAM");
        if (signSet != null && signSet.size() > 0) {
            signSet.stream().forEach(sign -> {
                List<Sign> list = (List<Sign>) redisService.get(sign);
                // 漏点 或 人未到的情况 直接设为未点到(后续可找相关角色补签)
                list.stream().forEach(lists -> {
                    if (lists.getSign() == null || lists.getSign().equals("") && lists.getSignTime() == null || lists.getSignTime().equals("")) {
                        lists.setSign("未点到");
                    }
                    if (lists.getSignTime() == null || lists.getSignTime().equals("")) {
                        lists.setSignTime("1970-01-01 00:00:00");
                    }
                });
                signList.addAll(list);
            });
            Integer integer = signService.putAll(signList);
            redisService.del("RBAC_SYSTEM:SIGN:TODAY_TEAM");
        } else {
            System.out.println("无签到表生成");
        }
    }
}
