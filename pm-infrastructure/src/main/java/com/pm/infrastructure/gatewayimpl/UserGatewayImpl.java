package com.pm.infrastructure.gatewayimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pm.infrastructure.dataobject.UserDO;
import com.pm.infrastructure.mapper.UserMapper;
import com.zyzh.pm.domain.gateway.UserGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wcy
 */
@Component
public class UserGatewayImpl implements UserGateway {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean existForUsername(String username) {
        UserDO userDO = userMapper.selectOne(new LambdaQueryWrapper<UserDO>()
                .eq(UserDO::getUsername, username)
        );
        return userDO != null;
    }
}
