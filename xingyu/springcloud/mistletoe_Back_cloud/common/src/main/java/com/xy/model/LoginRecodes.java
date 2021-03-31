package com.xy.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LoginRecodes implements Serializable {
    private Integer id;

    private String userNumber;

    private String userName;

    private String loginDate;

    private String loginPc;

    private String loginIp;
}
