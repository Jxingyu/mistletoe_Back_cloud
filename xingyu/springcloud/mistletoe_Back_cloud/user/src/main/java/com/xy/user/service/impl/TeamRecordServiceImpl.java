package com.xy.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.user.mapper.TeamRecordMapper;
import com.xy.model.TeamRecord;
import com.xy.user.service.TeamRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Vector;

@Service
public class TeamRecordServiceImpl extends ServiceImpl<TeamRecordMapper, TeamRecord> implements TeamRecordService {
    @Autowired
    TeamRecordMapper teamRecordMapper;

    @Override
    public Vector<TeamRecord> findTeamRecord() {
        return teamRecordMapper.findTeamRecord();
    }
}
