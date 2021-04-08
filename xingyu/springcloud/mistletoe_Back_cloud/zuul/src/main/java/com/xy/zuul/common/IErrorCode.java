package com.xy.zuul.common;

/**
 * 封装API的错误码
 * Created by xingyu.mall on 2021/1/13.
 */
public interface IErrorCode {

    long getCode();

    String getMessage();
}
