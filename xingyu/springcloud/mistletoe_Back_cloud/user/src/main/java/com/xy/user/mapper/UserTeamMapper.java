package com.xy.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.xy.model.Team;
import com.xy.model.User;
import com.xy.model.UserCaptainRelation;
import com.xy.model.UserTeamRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Vector;
@Repository
public interface UserTeamMapper extends BaseMapper<UserTeamRelation> {
    List<User> selectUserByTeamNumber();

    Vector<User> selectUserByTeamId(int id);

    Vector<UserTeamRelation> selectUtr(int id);

    Integer insertUserTeam(@Param("utr") List<UserTeamRelation> utr);

    Integer deleteUtrIdInUtr(@Param("utr") List<UserTeamRelation> utr);

    List<User> selectCaptainInUser();

    List<Team> selectCaptainIsUsername(int id);

    List<UserCaptainRelation> selectUcr(int id);

    List<UserTeamRelation> findPastTeamRecord(@Param("uUserId") int uUserId);

    Integer getUsername(int iUserId);

//    void deleteOldUTR(@Param("utr") List<UserTeamRelation> utr);
    void deleteOldUTR(int uUserId);
}
