package com.pm.application.service.impl;

import com.pm.infrastructure.consts.ErrorCodeEnum;
import com.pm.infrastructure.dataobject.UserDO;
import com.pm.infrastructure.mapper.RoleMapper;
import com.pm.infrastructure.mapper.UserMapper;
import com.pm.infrastructure.security.SecurityUser;
import com.zyzh.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

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
        return assembleSecurityUser(username, userDO, roleList);
    }

    private SecurityUser assembleSecurityUser(String username, UserDO userDO, Set<String> roleList) {
        SecurityUser securityUser = new SecurityUser(username, userDO.getPassword(), roleList);
        securityUser.setId(userDO.getId());
        securityUser.setName(userDO.getName());
        securityUser.setAvatar(userDO.getIcon());
        securityUser.setEmail(userDO.getEmail());
        return securityUser;
    }

}
