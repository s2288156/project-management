package com.pm.infrastructure.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

/**
 * @author wcy
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SecurityUser implements UserDetails {
    private static final long serialVersionUID = 1988301320280808093L;

    private String id;

    private String password;

    private String username;

    private Set<SimpleGrantedAuthority> authorities;

    public SecurityUser() {
    }

    public SecurityUser(String username, String password, Set<SimpleGrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Set<SimpleGrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
        return true;
    }
}
