package org.dev.framework.modules.sys.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;

import lombok.Data;
import lombok.experimental.Accessors;
import org.dev.framework.core.entity.TenantEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 * 用户基本信息
 * </p>
 *
 * @author hlt
 * @since 2020-06-24
 */
@Data
@Accessors(chain = true)
public class SysUser extends TenantEntity implements UserDetails {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 登录名
     */
    private String loginName;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 密码
     */
    private String password;
    /**
     * 性别
     */
    private String gender;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号码
     */
    private String mobilePhone;
    /**
     * 工号
     */
    private String workNum;
    /**
     * 微信号
     */
    private String wechat;
    /**
     * QQ号
     */
    private String qq;
    /**
     * 传真
     */
    private String fax;
    /**
     * 最后登录时间
     */
    private Date lastLoginTime;
    /**
     * 有效期
     */
    private Date expireTime;
    /**
     * 是否禁用 1启用 0禁用
     */
    private Integer isEnable;
    /**
     * 用户头像
     */
    private String userImg;
    /**
     * 用户皮肤
     */
    private String userSkin;


    @TableField(exist = false)
    private List<SysRole> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SysRole sysRole = new SysRole();
        sysRole.setRoleCode("ROLE_LOGIN");
        sysRole.setRoleName("默认角色");
        sysRole.setMemo("设置默认角色用于访问需要登录访问但不需要特殊权限的接口");
        if (roles == null) {
            roles = new ArrayList<>();
        }
        roles.add(sysRole);
        return roles;
    }

    @Override
    public String getUsername() {
        return loginName;
    }

    @Override
    public boolean isAccountNonExpired() {
        if (expireTime == null) {
            return true;
        }
        return expireTime.compareTo(new Date()) > 0 ? true : false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnable == 1 ? true : false;
    }
}
