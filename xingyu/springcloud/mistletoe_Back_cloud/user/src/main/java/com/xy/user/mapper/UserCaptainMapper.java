package com.xy.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.model.UserCaptainRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserCaptainMapper extends BaseMapper<UserCaptainRelation> {
    int updateNowCaptain(@Param("ucr") List<UserCaptainRelation> ucr);

    void updateTeamCaptainColumn(@Param("userId") Integer userId, @Param("numbers") Integer numbers);
}
