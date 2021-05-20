package com.pm.infrastructure.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author wcy
 */
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SecurityUser extends JwtPayload implements UserDetails {
    private static final long serialVersionUID = 1988301320280808093L;

    private String id;

    private String password;

    private String username;

    private Set<SimpleGrantedAuthority> authorities;

    public SecurityUser(String username, String password, Set<String> roles) {
        this.username = username;
        this.password = password;
        this.authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    public JwtPayload generalPayload() {
        JwtPayload jwtPayload = SecurityConvertor.INSTANCE.securityUser2JwtPayload(this);
        jwtPayload.setRoles(roles());
        jwtPayload.setExp(expDaysLater());
        return jwtPayload;
    }

    public Set<String> roles() {
        return this.authorities.stream()
                .map(SimpleGrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
    }

    private long expDaysLater() {
        return LocalDateTime.now().plusDays(30).toEpochSecond(ZoneOffset.of("+8"));
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
