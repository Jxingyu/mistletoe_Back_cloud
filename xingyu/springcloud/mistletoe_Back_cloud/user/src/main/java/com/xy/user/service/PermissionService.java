package com.xy.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.model.Permission;
import com.xy.model.RolePermissionRelation;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * <p>
 * 后台用户权限表 服务类
 * </p>
 *
 * @author xingyu
 * @since 2021-01-21
 */
public interface PermissionService extends IService<Permission> {

    List selectPermission();

    ArrayList selectPmsByRoleId(Long id);

    Integer insertRolePms(List<RolePermissionRelation> rpr);

    List selectRpr(Long id);

    Integer deleteRpr(List<RolePermissionRelation> rpr);

    ArrayList<Permission> findPermissionsByRoleId(Integer roleId);

    ArrayList<Permission> findAllPermission();

    Vector<Permission> findPerByRoleId(Integer id);
}
