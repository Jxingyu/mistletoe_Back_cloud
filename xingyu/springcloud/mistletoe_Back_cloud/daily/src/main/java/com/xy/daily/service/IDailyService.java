package com.xy.daily.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.model.Daily;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Vector;

public interface IDailyService extends IService<Daily> {
    Vector<Daily> selectDailyAll(Integer userId);

    Integer selectDailyId(String username);

    Vector<Daily> selectDailyEditMes(Integer userId);

   int  insertDaily(Daily daily);

    int saveDailyInRedis(Daily daily);

    Vector<Daily> selectDailyById(int id);

    int updateDraftDaily(Daily daily);

    int updateDailyStatus(int id,String status);

    Vector<Daily> ReviewDailyAll( );

    String ExportWord(Daily daily, HttpServletResponse response) throws Exception;

    /**
     *
     * @param pathName 文件路径 和 文件名称
     * @param response 响应头
     */
    String downLoad(String pathName,int id, HttpServletResponse response) throws IOException;


}
