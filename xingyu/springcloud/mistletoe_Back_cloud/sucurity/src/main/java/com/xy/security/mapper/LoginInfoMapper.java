package com.xy.security.mapper;


import com.xy.model.LoginRecodes;

import java.util.List;

public interface LoginInfoMapper {
    List<LoginRecodes> findAllLoginInfos();
}
