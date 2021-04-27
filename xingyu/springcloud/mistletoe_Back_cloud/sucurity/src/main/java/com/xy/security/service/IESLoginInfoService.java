package com.xy.security.service;


import com.xy.model.LoginRecodes;
import com.xy.security.model.EsLoginInfo;
import org.elasticsearch.search.SearchHits;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface IESLoginInfoService {
    /**
     * 从数据库中导入所有登录记录到ES
     */
    boolean importAll() throws IOException;

    /**
     * 根据id删除记录
     */
    void delete(String id) throws IOException;

    boolean ifCreateIndex() throws IOException;
    /**
     * 根据关键字搜索用户名
     */
    List<EsLoginInfo> search(String keyWord) throws IOException;

    void deleteById(Long id);

    Page esLoginService(String keyword, Integer pageNum, Integer pageSize);

    SearchHits searchHit(String keyword, Integer pageNum, Integer pageSize);

    void putEsLogin(EsLoginInfo esLoginInfo) throws IOException;
}
