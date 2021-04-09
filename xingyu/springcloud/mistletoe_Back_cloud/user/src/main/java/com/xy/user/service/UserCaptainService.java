package com.xy.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.model.UserCaptainRelation;

import java.util.List;

public interface UserCaptainService extends IService<UserCaptainRelation> {
    int updateNowCaptain(List<UserCaptainRelation> ucr);
}
