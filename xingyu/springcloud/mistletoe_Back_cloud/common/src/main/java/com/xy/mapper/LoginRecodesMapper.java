package com.xy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.model.LoginRecodes;
import org.springframework.stereotype.Repository;

import java.util.Vector;

/**
 * <p>
 * 后台登录日志 Mapper 接口
 * </p>
 *
 * @author xingyu
 * @since 2021-01-21
 */
@Repository
public interface LoginRecodesMapper extends BaseMapper<LoginRecodes> {
    Vector<LoginRecodes> SelectAllLoginRecodes(LoginRecodes loginRecodes);
}
