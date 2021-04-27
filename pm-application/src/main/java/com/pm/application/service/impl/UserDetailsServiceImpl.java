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
        Optional<UserDO> userOptional = userMapper.selectForUsername(username);
        if (!userOptional.isPresent()) {
            throw new BizException(ErrorCodeEnum.USERNAME_NOT_FOUND);
        }
        UserDO userDO = userOptional.get();
        Set<String> roleList = roleMapper.listRoleByUid(userOptional.get().getId());
        Set<SimpleGrantedAuthority> authorities = roleList.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        return assembleSecurityUser(username, userDO, authorities);
    }

    private SecurityUser assembleSecurityUser(String username, UserDO userDO, Set<SimpleGrantedAuthority> authorities) {
        SecurityUser securityUser = new SecurityUser(username, userDO.getPassword(), authorities);
        securityUser.setId(userDO.getId());
        securityUser.setName(userDO.getName());
        securityUser.setAvatar(userDO.getIcon());
        securityUser.setEmail(userDO.getEmail());
        return securityUser;
    }

}
