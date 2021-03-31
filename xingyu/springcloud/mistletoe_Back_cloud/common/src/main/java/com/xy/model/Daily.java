package com.xy.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
// swagger 实体类上
@ApiModel(value = "日报实体类", description = "用于日报管理")
public class Daily implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "用户ID", example = "6")
    private Integer userId;
    @ApiModelProperty(value = "用户名", example = "汤姆.谢尔比")// swagger 属性上
    private String username;
    @ApiModelProperty(value = "填写日报日期", example = "2020-02-02-xxx..")
    private String dailyTitle;

    private String coach;

    private String assistant;

    private String dailyContent;

    private String harvest;

    private String shortage;

    private String submitDate;

    /**
     * 状态0-草稿  1-已提交  2-已查看
     */
    private String status;

}
