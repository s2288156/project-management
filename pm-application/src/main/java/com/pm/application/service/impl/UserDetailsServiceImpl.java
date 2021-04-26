package com.pm.application.service.impl;

import com.pm.application.consts.ErrorCodeEnum;
import com.pm.infrastructure.dataobject.UserDO;
import com.pm.infrastructure.mapper.UserMapper;
import com.zyzh.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author wcy
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

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
        // TODO: 2021/4/25 临时写死Roles
        Set<SimpleGrantedAuthority> authorityList = new HashSet<>();
        authorityList.add(new SimpleGrantedAuthority("ADMIN"));
        return new User(username, userDO.get().getPassword(), authorityList);
    }
}
