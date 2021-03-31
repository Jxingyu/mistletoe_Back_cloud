package com.xy.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.model.Permission;
import com.xy.model.User;
import com.xy.model.UserTeamRelation;
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

//    List<User> checkUser(User user);

    User getUserByUsername(String username);

    List<Permission> getPermissionByRoleId(Integer id);

    List<User> getUserById(Integer idOne);

    Vector<User> findAll(User user);

    Integer playerUpdate(User user);

    Integer uploadFile(User user);

    User findPlayerById(Integer id);

    int findTotalCount(User user);

    Vector findPlayerAll(User user);

    Integer register(User user);

    Integer getMaxId();

    Integer registerIconUpdate(User user);

    void urrInsert(Integer maxId);

    List<UserTeamRelation> findUserByTeamNumbers(Integer numbers);

    String selectTeamNameByNumbers(Integer numbers);

    String selectUsernameByUserId(Integer userId);

    void insertLoginRecords(@Param("userName") String userName, @Param("pcName") String pcName, @Param("pcIp") String pcIp, @Param("loginDate") String loginDate);
}
