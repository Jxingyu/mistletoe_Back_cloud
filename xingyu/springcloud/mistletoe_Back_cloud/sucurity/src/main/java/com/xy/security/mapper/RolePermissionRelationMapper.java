package com.xy.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.model.Permission;
import com.xy.model.RolePermissionRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * <p>
 * 后台用户角色和权限关系表 Mapper 接口
 * </p>
 *
 * @author xingyu
 * @since 2021-01-21
 */
@Repository
public interface RolePermissionRelationMapper extends BaseMapper<RolePermissionRelation> {

    Integer insertRolePms(@Param("rpr") List<RolePermissionRelation> rpr);

    Integer deleteRpr(@Param("rpr")List<RolePermissionRelation> rpr);

    ArrayList<Permission> findPermissionsByRoleId(Integer roleId);

    ArrayList<Permission> findAllPermission();

    Vector<Permission> findPerByRoleId(Integer id);
}
