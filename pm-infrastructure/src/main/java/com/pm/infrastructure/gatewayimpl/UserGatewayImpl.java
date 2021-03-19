package com.pm.infrastructure.gatewayimpl;

import com.pm.infrastructure.dataobject.UserDO;
import com.pm.infrastructure.mapper.UserMapper;
import com.zyzh.pm.domain.gateway.UserGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author wcy
 */
@Component
public class UserGatewayImpl implements UserGateway {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean existForUsername(String username) {
        Optional<UserDO> userOptional = userMapper.selectForUsername(username);
        return userOptional.isPresent();
    }
}
