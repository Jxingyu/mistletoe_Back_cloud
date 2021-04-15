package com.xy.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.model.LoginRecodes;
import com.xy.security.mapper.LoginRecodesMapper;
import com.xy.security.service.ILoginRecodesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Vector;

@Service
public class iLoginRecodesServiceImpl extends ServiceImpl<LoginRecodesMapper, LoginRecodes> implements ILoginRecodesService {
    @Autowired
    private LoginRecodesMapper loginRecodesMapper;
    @Override
    public Vector<LoginRecodes> SelectAllLoginRecodes(LoginRecodes loginRecodes) {
        Vector<LoginRecodes>  vector = loginRecodesMapper.SelectAllLoginRecodes(loginRecodes);
        return vector;
    }
}
