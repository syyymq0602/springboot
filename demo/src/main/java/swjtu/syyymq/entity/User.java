package swjtu.syyymq.entity;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class User implements UserDetails {

    private Integer id;
    private String username;
    private String password;
    private Boolean enabled = true;
    private Boolean locked = false;
    private Boolean expired = false;
    private Boolean credentialsExpire = false;

    private List<Role> roles;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public void setCredentialsExpire(Boolean credentialsExpire) {
        this.credentialsExpire = credentialsExpire;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", locked=" + locked +
                ", expired=" + expired +
                ", credentialsExpire=" + credentialsExpire +
                ", roles=" + roles +
                '}';
    }

    @Override
    // 自动授予用户权限
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getNameEN()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    // 账户是否过期
    public boolean isAccountNonExpired() {
        return !expired;
    }

    @Override
    // 账户是否被锁定
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    // 凭证是否过期
    public boolean isCredentialsNonExpired() {
        return !credentialsExpire;
    }

    @Override
    // 账户是否可用
    public boolean isEnabled() {
        return enabled;
    }
}
