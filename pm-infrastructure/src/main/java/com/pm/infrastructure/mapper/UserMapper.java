package com.pm.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pm.infrastructure.dataobject.UserDO;

import java.util.Optional;

/**
 * @author wcy
 */
public interface UserMapper extends BaseMapper<UserDO> {
    Optional<UserDO> selectForUsername(String username);
}
