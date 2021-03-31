package com.xy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.model.Role;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * <p>
 * 后台用户角色表 Mapper 接口
 * </p>
 *
 * @author xingyu
 * @since 2021-01-21
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    List findRoleNameById(Integer id);

    List findNowRoleByUserId(Integer id);

    Integer updateUserRole(Role role);

    Integer updateUserRoleTwo(Role role);

    List findAll();

    ArrayList findAllRoles();
}
