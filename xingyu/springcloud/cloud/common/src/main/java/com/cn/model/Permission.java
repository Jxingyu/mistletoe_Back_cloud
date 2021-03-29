package com.cn.model;


import java.time.LocalDateTime;
import java.io.Serializable;


/**
 * <p>
 * 后台用户权限表
 * </p>
 *
 * @author xingyu
 * @since 2021-01-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString

public class Permission implements Serializable , GrantedAuthority {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 权限id
     */
    private Long pid;

    /**
     * 名称
     */
    private String name;

    /**
     * 权限值
     */
    private String value;

    /**
     * 图标
     */
    private String icon;

    /**
     * 权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）
     */
    private Integer type;

    /**
     * 前端资源路径
     */
    private String uri;

    /**
     * 启用状态；0->禁用；1->启用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     *
     */
    private Integer permissionId;

    /**
     * 手动添加权限值
     * @return
     */
    @Override
    public String getAuthority() {
        return value;
    }
}
