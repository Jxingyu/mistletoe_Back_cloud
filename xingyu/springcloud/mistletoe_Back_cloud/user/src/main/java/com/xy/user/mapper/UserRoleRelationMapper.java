package com.xy.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.model.UserRoleRelation;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 后台用户和角色关系表 Mapper 接口
 * </p>
 *
 * @author xingyu
 * @since 2021-01-21
 */
@Repository
public interface UserRoleRelationMapper extends BaseMapper<UserRoleRelation> {

    int roleNotNull(Integer id);
}
