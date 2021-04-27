package com.pm.application.service.impl;

import com.pm.application.consts.ErrorCodeEnum;
import com.pm.infrastructure.dataobject.UserDO;
import com.pm.infrastructure.mapper.RoleMapper;
import com.pm.infrastructure.mapper.UserMapper;
import com.pm.infrastructure.security.SecurityUser;
import com.zyzh.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author wcy
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    /**
     * Role实现类{@link org.springframework.security.core.authority.SimpleGrantedAuthority}
     *
     * @return {@link org.springframework.security.core.userdetails.User}
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDO> userDO = userMapper.selectForUsername(username);
        if (!userDO.isPresent()) {
            throw new BizException(ErrorCodeEnum.USERNAME_NOT_FOUND);
        }
        Set<String> roleList = roleMapper.listRoleByUid(userDO.get().getId());
        Set<SimpleGrantedAuthority> authorities = roleList.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        return new SecurityUser(username, userDO.get().getPassword(), authorities);
    }
}
