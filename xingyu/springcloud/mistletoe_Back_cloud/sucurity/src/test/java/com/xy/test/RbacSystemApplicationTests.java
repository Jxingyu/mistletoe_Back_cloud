package com.xy.test;

import com.xy.security.model.EsLoginInfo;
import com.xy.security.service.IESLoginInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Date;

@SpringBootTest
public class RbacSystemApplicationTests {
    @Autowired
    IESLoginInfoService iesLoginInfoService;

    @Test
    void ESPutTest() {
        EsLoginInfo info = new EsLoginInfo();
        info.setUsername("法外狂徒");
        info.setLoginPc("bbx");
        info.setLoginIp("127.0.0.1");
        info.setLoginDate(String.valueOf(new Date(System.currentTimeMillis())));
        try {
            iesLoginInfoService.putEsLogin(info);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
