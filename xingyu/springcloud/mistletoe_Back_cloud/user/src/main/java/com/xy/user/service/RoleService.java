package com.xy.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.model.Role;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 后台用户角色表 服务类
 * </p>
 *
 * @author xingyu
 * @since 2021-01-21
 */
public interface RoleService extends IService<Role> {

    List findRoleNameById(Integer id);

    List findNowRoleByUserId(Integer id);

    Integer updateUserRole(Role role);

    Integer updateUserRoleTwo(Role role);

    List findAll();

    ArrayList findAllRoles();
}
