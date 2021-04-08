package com.xy.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import lombok.*;
import lombok.experimental.Accessors;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 * 后台用户表
 * </p>
 *
 * @author xingyu
 * @since 2021-01-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {//

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;
    /**
     * 角色
     */
    private String role;
    /**
     * sex
     */
    private String sex;
    /**
     * phone
     */
    private String phone;
    /**
     * address
     */
    private String address;

    /**
     * 最后修改时间
     */
    private String editTime;
    /**
     * /**
     * 头像
     */
    private String icon;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 备注信息
     */
    private String contend;

    /**
     * 注册时间
     */
    private LocalDateTime createTime;

    /**
     * 最后登录时间
     */
    private String loginTime;

    /**
     * 帐号启用状态：0->禁用；1->启用
     */
    private Integer status;

    /**
     * 验证码(生成Model用)
     */
    private String captcha;

//    /**
//     * 权限列表(接收getAuthorities()用)
//     */
//    private List<? extends GrantedAuthority> authorities;

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

    /**
     * 点到状态
     */
    private  String sign;


//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities(){
//        return this.authorities;
//    }
//    @Override
//    public String getPassword(){
//        return this.password;
//    }
//
//
//    @Override
//    public String getUsername(){
//        return this.username;
//    }
//
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
}
