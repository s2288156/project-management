package com.pm.application.command;

import com.alibaba.cola.dto.Response;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pm.infrastructure.consts.ErrorCodeEnum;
import com.pm.infrastructure.dataobject.DependenceDO;
import com.pm.infrastructure.mapper.DependenceMapper;
import com.pm.infrastructure.mapper.GroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author wcy
 */
@Component
public class GroupDeleteExe {

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private DependenceMapper dependenceMapper;

    @Autowired
    private ProjectDeleteCmdExe projectDeleteCmdExe;

    @Transactional(rollbackFor = Exception.class)
    public Response execute(String id) {
        List<String> allMid = groupMapper.listAllMidByGroupId(id);
        List<DependenceDO> dependenceDOS = dependenceMapper.selectList(new LambdaQueryWrapper<DependenceDO>().in(DependenceDO::getDependMid, allMid));
        if (CollectionUtils.isEmpty(dependenceDOS)) {
            groupMapper.deleteById(id);
            return Response.buildSuccess();
        }
        return Response.buildFailure(ErrorCodeEnum.HAVE_DEPEND_GROUP_NOT_ALLOW_DELETE.getCode(), ErrorCodeEnum.HAVE_DEPEND_GROUP_NOT_ALLOW_DELETE.getMsg());
    }
}
