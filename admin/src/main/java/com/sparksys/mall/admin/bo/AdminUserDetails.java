package com.sparksys.mall.admin.bo;

import com.sparksys.mall.admin.entity.UmsAdmin;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 中文类名: SpringSecurity需要的用户详情 中文描述: SpringSecurity需要的用户详情
 *
 * @author zhouxinlei
 * @date 2019-10-15 10:18:52
 */
@Data
public class AdminUserDetails implements UserDetails {
    /**
     *
     */
    private static final long serialVersionUID = -7011302902790709870L;
    private UmsAdmin umsAdmin;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 返回当前用户的权限
        return umsAdmin.getPermissions().stream().map(permission -> new SimpleGrantedAuthority(permission))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return umsAdmin.getPassword();
    }

    @Override
    public String getUsername() {
        return umsAdmin.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
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
        return umsAdmin.getStatus().equals(1);
    }
}
