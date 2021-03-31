package com.xy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.model.TeamRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Vector;
@Repository
public interface TeamRecordMapper extends BaseMapper<TeamRecord> {
    Vector<TeamRecord> findTeamRecord();

    void insertPastTeamRecord(@Param("sUserId") Integer sUserId, @Param("xUserId") Integer xUserId, @Param("xNumbers") Integer xNumbers);

    void updateCurrentTeamRecord(@Param("iNumbers") Integer iNumbers, @Param("iUserId") Integer iUserId);
}
