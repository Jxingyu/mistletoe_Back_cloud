package com.xy.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.model.UserTeamRelation;
import com.xy.model.Permission;
import com.xy.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Vector;

/**
 * <p>
 * 后台用户表 Mapper 接口
 * </p>
 *
 * @author xingyu
 * @since 2021-01-21
 */
@Repository
public interface LoginUserMapper extends BaseMapper<User> {

    void insertLoginRecords(@Param("userName") String userName, @Param("pcName") String pcName, @Param("pcIp") String pcIp, @Param("loginDate") String loginDate);
}
