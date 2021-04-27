package com.xy.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Document(indexName = "es_login_info", shards = 1, replicas = 1)//indexName不要驼峰命名 shards 切片 默认1 replicas备份 默认1
public class EsLoginInfo {
    //date 格式要求 yyyy-MM-dd HH:mm:ss 或者 时间戳
//    private Long id;
//    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String loginInfoId;
    private String username;
    private String loginDate;
    private String loginPc;
    private String loginIp;



//        public String getLoginTime() {
//                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd ");
//                format.format(loginTime)
//                return loginTime;
//        }
}
