package com.xy.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.common.CommonResult;
import com.xy.model.Team;
import com.xy.model.User;
import com.xy.model.UserCaptainRelation;
import com.xy.model.UserTeamRelation;


import java.util.List;
import java.util.Vector;

public interface UserTeamService extends IService<UserTeamRelation> {

    List<User> selectUserByTeamNumber();

    Vector<User> selectUserByTeamId(int id);

    Vector<UserTeamRelation> selectUtr(int id);

    CommonResult insertUserTeam(List<UserTeamRelation> utr);

    Integer deleteUtrIdInUtr(List<UserTeamRelation> utr);

    List<User> selectCaptainInUser();

    List<Team> selectCaptainIsUsername(int id);

    List<UserCaptainRelation> selectUcr(int id);

}
