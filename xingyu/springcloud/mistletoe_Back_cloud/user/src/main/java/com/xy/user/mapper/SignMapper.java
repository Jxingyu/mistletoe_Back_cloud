package com.xy.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.model.Sign;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Vector;

@Repository
public interface SignMapper extends BaseMapper<Sign> {
    String select(Integer userId);

    String selectUserName(Integer userId);


    Integer putAll(List<Sign> signList);

    Vector<Sign> selectSignRecord(Sign sign);

    int selectTotalCount(Sign sign);

    int updateSign(Sign sign);

    String findSignById(int id);
}
