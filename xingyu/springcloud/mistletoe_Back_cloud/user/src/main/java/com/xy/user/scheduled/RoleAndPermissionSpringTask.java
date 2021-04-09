package com.xy.user.scheduled;


import cn.hutool.core.collection.CollectionUtil;
import com.xy.model.Permission;
import com.xy.model.Role;
import com.xy.service.impl.RedisServiceImpl;
import com.xy.user.mapper.PermissionMapper;
import com.xy.user.service.PermissionService;
import com.xy.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;

@Component//@Component （把普通pojo实例化到spring容器中，相当于配置文件中的）
//泛指各种组件，就是说当我们的类不属于各种归类的时候（不属于@Controller、@Services等的时候），我们就可以使用@Component来标注这个类。
public class RoleAndPermissionSpringTask {
    @Autowired
    RedisServiceImpl redisService;

    @Autowired
    PermissionMapper permissionMapper;

    @Autowired
    PermissionService permissionService;

    @Autowired
    RoleService roleService;

    /***
     *
     * @deprecated 每小时更新redis中角色信息
     */
    @Scheduled(cron = "0 0 * * * ?")
//    @Scheduled(cron = "*/5 * * ? * ?") //五秒测试
    public void UpdateAllRolesAndPermissionsInRedisPerHour() {
        redisService.del("RBAC_SYSTEM:ROLE:ROLES", "RBAC_SYSTEM:PERMISSION:ALL_PERMISSIONS");

        ArrayList allRoles = roleService.findAllRoles();
        ArrayList<Role> roles = (ArrayList<Role>) allRoles;//所有角色信息allRoles
        roles.stream().forEach(x -> {
            // 拿到的所有角色的ID -> 去查询每个角色ID对应的权限
            ArrayList<Permission> permissionsByRoleId = permissionService.findPermissionsByRoleId(x.getId());//permissionsByRoleId
            HashSet<Permission> permissions = CollectionUtil.newHashSet(permissionsByRoleId);// 用new转为HashSet去重
            x.setPermissions(new ArrayList<>(permissions)); // 转回ArrayList封装
        });
        redisService.set("RBAC_SYSTEM:ROLE:ROLES", roles);// 存入每个角色当前所有权限

        ArrayList<Permission> permissions = permissionService.findAllPermission();// 存入当前所有权限
        redisService.set("RBAC_SYSTEM:PERMISSION:ALL_PERMISSIONS",permissions);
    }
}
