package com.xy.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.common.CommonResult;
import com.xy.security.model.Permission;
import com.xy.security.model.User;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author xingyu
 * @since 2021-01-21
 */
public interface SecurityUserService extends IService<User> {

    Object login(User user, HttpServletRequest request);

    User getUserByUsername(String username);
//
    List<Permission> getPermissionByRoleId(Integer id);

    CommonResult checkAccessToUri(String uri, String username);
//
//    Vector findAll(User user);
//
//    Integer playerUpdate(User user);
//
//    Integer uploadFile(User user);
//
//    User findPlayerById(Integer id);
//
//    int findTotalCount(User user);
//
//    Vector findPlayerAll(User user);
//
//    CommonResult register(User user);
//
//    Integer registerIconUpdate(User user);
//
//
//    List<UserTeamRelation> findUserByTeamNumbers(Integer numbers);
//
//    String selectTeamNameByNumbers(Integer numbers);
//
//    String selectUsernameByUserId(Integer userId);
}
