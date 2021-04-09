package com.xy.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.model.Team;
import com.xy.user.mapper.TeamMapper;
import com.xy.user.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Vector;

@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements TeamService {
    @Autowired
    TeamMapper teamMapper;

    @Override
    public Vector<Team> findTeamAll() {
        return teamMapper.findTeamAll();
    }

    @Override
    public Integer editUpdate(Team team) {
        return teamMapper.editUpdate(team);
    }

    @Override
    public Vector<Team> findTeamById(int numbers) {
        return teamMapper.findTeamById(numbers);
    }

    @Override
    public int deleteTeam(int numbers) {
        return teamMapper.deleteTeam(numbers);
    }

    @Override
    public List<Team> findAllTeam() {
        return teamMapper.findAllTeam();
    }


}
