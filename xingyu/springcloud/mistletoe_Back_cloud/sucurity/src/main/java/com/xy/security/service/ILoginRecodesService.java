package com.xy.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.model.LoginRecodes;

import java.util.Vector;

public interface ILoginRecodesService extends IService<LoginRecodes> {
    Vector<LoginRecodes> SelectAllLoginRecodes(LoginRecodes loginRecodes);
}
