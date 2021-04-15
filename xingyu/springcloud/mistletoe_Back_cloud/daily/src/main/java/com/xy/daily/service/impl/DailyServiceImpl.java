package com.xy.daily.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.daily.service.IDailyService;
import com.xy.daily.mapper.DailyMapper;
import com.xy.model.Daily;
import com.xy.service.RedisService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements IDailyService {
    @Autowired
    DailyMapper dailyMapper;

    @Autowired
    RedisService redisService;

    @Override
    public Integer selectDailyId(String username) {
        Integer id = dailyMapper.selectDailyId(username);
        return id;
    }

    @Override
    public Vector<Daily> selectDailyAll(Integer userId) {
        Vector<Daily> vector = dailyMapper.selectDailyAll(userId);
        return vector;
    }

    @Override
    public Vector<Daily> selectDailyEditMes(Integer userId) {
        // 查询团队区分用numbers
        Integer numbers = dailyMapper.selectNumbersByUserId(userId);
        Vector<Daily> dailies = dailyMapper.selectDailyEditMes(numbers);
        return dailies;
    }

    @Override
    public int insertDaily(Daily daily) {
        int i = dailyMapper.insertDaily(daily);
        return i;
    }

    @Override
    public int saveDailyInRedis(Daily daily) {
        /*List<Daily> list = Collections.singletonList(daily);
        list.stream().forEach(x -> {
            redisService.set("RBAC_SYSTEM:DAILY:DRAFT_TEAM" +"_"+ x.getUserId(), x);

        });*/
        int i = dailyMapper.saveDailyInRedis(daily);
        return i;
    }

    @Override
    public Vector<Daily> selectDailyById(int id) {
        return dailyMapper.selectDailyById(id);
    }

    @Override
    public int updateDraftDaily(Daily daily) {
        return dailyMapper.updateDraftDaily(daily);
    }

    @Override
    public int updateDailyStatus(int id, String status) {
        return dailyMapper.updateDailyStatus(id, status);
    }

    @Override
    public Vector<Daily> ReviewDailyAll( ) {
        Vector<Daily> vector = dailyMapper.ReviewDailyAll();
        return vector;
    }

    /**
     * 生成word文档
     *
     * @param daily
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    public String ExportWord(Daily daily, HttpServletResponse response) throws Exception {
        Map<String, String> dataMap = new HashMap();
        String GetPath = "";
        try {
            //姓名
            dataMap.put("username", daily.getUsername());
            //日期
            dataMap.put("submitDate", daily.getSubmitDate());
            // 教练
            dataMap.put("coach", daily.getCoach());
            // 助理
            dataMap.put("assistant", daily.getAssistant());
            // 日报标题
            dataMap.put("dailyTitle", daily.getDailyTitle());
            //学习内容
            dataMap.put("dailyContent", daily.getDailyContent());
            //收获
            dataMap.put("harvest", daily.getHarvest());
            //不足
            dataMap.put("shortage", daily.getShortage());
            //Configuration 用于读取ftl文件
            Configuration configuration = new Configuration(new Version("2.3.0"));
            configuration.setDefaultEncoding("utf-8");
            /**
             * 指定ftl文件所在目录的路径，而不是ftl文件的路径
             */
            //路径是D:\JavaProject\CreateWord\日报.ftl
            configuration.setDirectoryForTemplateLoading(new File("D:\\JavaProject\\CreateWord\\"));
            //输出文档路径及名称
            File outFile = new File("D:\\JavaProject\\CreateWord\\" + daily.getId() + dataMap.get("username") + ".doc");
            GetPath = (String.valueOf(outFile));
            //以utf-8的编码读取ftl文件
            Template template = configuration.getTemplate("model.ftl", "utf-8");
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"), 10240);
            template.process(dataMap, out);
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return GetPath;

    }

    /**
     * word文件 响应浏览器下载
     *
     * @param pathName 文件路径 和 文件名称
     * @param response 响应头
     */
    @Override
    public String downLoad(String pathName, int id, HttpServletResponse response) throws IOException {
        String s = new String(pathName);
        String a[] = s.split(String.valueOf(id));// 以当前id为切割
        String wordName = a[1];// word文件名称
        //获取服务器文件
        String URL = pathName;

        try {
            String path = URL;
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + filename);
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Length", "" + file.length());
            OutputStream os = response.getOutputStream();
            os.write(buffer);
            os.flush();
            os.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
