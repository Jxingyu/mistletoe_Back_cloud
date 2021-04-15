package com.xy.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.model.TeamRecord;

import java.util.Vector;

public interface TeamRecordService extends IService<TeamRecord> {
    Vector<TeamRecord> findTeamRecord();

}
