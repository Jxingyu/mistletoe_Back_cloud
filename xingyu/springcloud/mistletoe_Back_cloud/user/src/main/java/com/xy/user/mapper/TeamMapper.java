package com.xy.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.model.Team;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Vector;
@Repository
public interface TeamMapper extends BaseMapper<Team> {

    Vector<Team> findTeamAll();

    Integer editUpdate(Team team);

    Vector<Team> findTeamById(int numbers);

    int deleteTeam(int numbers);

    List<Team> findAllTeam();

    List<Team> selectTeamNumbers();
}
