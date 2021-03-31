package com.xy.security.service.impl;

import com.xy.model.Permission;
import com.xy.model.User;
import com.xy.security.service.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService") // 提供从数据库查出来的用户(根据用户名或者账号)查出来的实体
public class MyUserDetailsImpl implements UserDetailsService {


    @Autowired
    private SecurityUserService securityUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List <Permission> list;
        User user = securityUserService.getUserByUsername(username);// 查出用户信息
        list = securityUserService.getPermissionByRoleId(user.getId());// 通过用户信息里的ID查出权限
//        HashSet<Permission> permissions = new HashSet<>(permissionList);// HashSet去重复
        List<Permission> permissions= new ArrayList (list);
        user.setAuthorities((List) permissions);// 封装权限
        return (UserDetails) user; // UserDetails类型
    }
}
