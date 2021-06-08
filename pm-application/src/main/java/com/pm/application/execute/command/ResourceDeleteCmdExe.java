package com.pm.application.execute.command;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pm.infrastructure.consts.ErrorCodeEnum;
import com.pm.infrastructure.dataobject.RoleResourceDO;
import com.pm.infrastructure.mapper.ResourceMapper;
import com.pm.infrastructure.mapper.RoleResourceMapper;
import com.zyzh.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wcy
 */
@Slf4j
@Component
public class ResourceDeleteCmdExe {

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    public void execute(String id) {
        Integer roleResourceCount = roleResourceMapper.selectCount(new LambdaQueryWrapper<RoleResourceDO>()
                .eq(RoleResourceDO::getResourceId, id));
        if (roleResourceCount > 0) {
            throw new BizException(ErrorCodeEnum.RESOURCE_USED_NOT_ALLOW_DEL);
        }
        resourceMapper.deleteById(id);
    }

}
