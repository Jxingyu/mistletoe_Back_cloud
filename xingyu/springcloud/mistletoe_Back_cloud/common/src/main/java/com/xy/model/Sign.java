package com.xy.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Sign implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)

    private Integer id;

    private String teamName;

    private Integer userId;

    private String username;

    private String signTime;

    private String sign;

    private Integer nowNumbers;

    /**
     * 当前页数
     */

    private Integer curPage;

    /**
     *变动页数size
     */
    private Integer pageSize;

    /**
     * 判断用开始时间
     */
    private String startDate;

    /**
     * 判断用结束时间
     */
    private  String endDate;
}
