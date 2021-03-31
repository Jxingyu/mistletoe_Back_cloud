package com.xy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.model.Daily;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Vector;
@Repository
public interface DailyMapper extends BaseMapper<Daily> {
    Vector<Daily> selectDailyAll(Integer userId);

    Integer selectDailyId(String username);

    Integer selectNumbersByUserId(Integer userId);

    Vector<Daily> selectDailyEditMes(Integer numbers);

    int insertDaily(Daily daily);

    int saveDailyInRedis(Daily daily);

    Vector<Daily> selectDailyById(int id);

    int updateDraftDaily(Daily daily);

    int updateDailyStatus(@Param("id") int id, @Param("status") String status);

    Vector<Daily> ReviewDailyAll( );
}
