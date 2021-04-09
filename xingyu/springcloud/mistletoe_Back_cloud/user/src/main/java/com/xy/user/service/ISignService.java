package com.xy.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.model.Sign;

import java.util.List;
import java.util.Vector;


public interface ISignService extends IService<Sign> {
    Boolean insertSignRecord(Sign sign);

    Integer putAll(List<Sign> signList);

    Vector<Sign> selectSignRecord(Sign sign);

    int selectTotalCount(Sign sign);

    int updateSign(Sign sign);

    String findSignById(int id);

}
