package com.pm.security;

import com.pm.infrastructure.dataobject.UserDO;
import com.pm.infrastructure.mapper.UserMapper;
import com.pm.infrastructure.security.JwtPayload;
import com.pm.infrastructure.security.SecurityUser;
import com.pm.infrastructure.security.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

/**
 * @author wcy
 */
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserMapper userMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            JwtPayload payload = tokenService.verifierAndGetPayload(request);
            UserDO userDO = userMapper.selectById(payload.getUid());
            SecurityUser securityUser = new SecurityUser(userDO.getUsername(), userDO.getPassword(), payload.getRoles());
            new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
        } catch (ParseException e) {
            log.error("jwt get payload parse error: ", e);
        }
        filterChain.doFilter(request, response);
    }
}
