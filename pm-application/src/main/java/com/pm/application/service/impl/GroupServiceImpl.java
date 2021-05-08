package com.pm.application.service.impl;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pm.infrastructure.consts.ErrorCodeEnum;
import com.pm.application.convertor.GroupConvertor;
import com.pm.application.dto.cmd.GroupAddCmd;
import com.pm.application.dto.vo.GroupVO;
import com.pm.application.service.IGroupService;
import com.pm.infrastructure.dataobject.DependenceDO;
import com.pm.infrastructure.dataobject.GroupDO;
import com.pm.infrastructure.entity.PageQuery;
import com.pm.infrastructure.entity.PageResponse;
import com.pm.infrastructure.mapper.DependenceMapper;
import com.pm.infrastructure.mapper.GroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author wcy
 */
@Service
public class GroupServiceImpl implements IGroupService {

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private DependenceMapper dependenceMapper;

    @Override
    public SingleResponse<String> addGroup(GroupAddCmd addCmd) {
        Optional<GroupDO> optional = groupMapper.selectByName(addCmd.getName());
        if (optional.isPresent()) {
            return SingleResponse.buildFailure(ErrorCodeEnum.GROUP_NAME_EXISTED.getErrorCode(), ErrorCodeEnum.GROUP_NAME_EXISTED.getErrorMsg());
        }
        GroupDO groupDO = GroupConvertor.convert2Do(addCmd);
        groupMapper.insert(groupDO);
        return SingleResponse.of(groupDO.getId());
    }

    @Override
    public PageResponse<GroupVO> listGroup(PageQuery pageQuery) {
        Page<GroupDO> page = pageQuery.createPage();
        groupMapper.selectPage(page, new LambdaQueryWrapper<>());

        List<GroupVO> datas = page.getRecords()
                .stream()
                .map(GroupVO::convertForDo)
                .collect(Collectors.toList());

        return PageResponse.of(datas, page.getTotal());
    }

    @Override
    public Response deleteById(String id) {
        List<String> allMid = groupMapper.listAllMidByGroupId(id);
        List<DependenceDO> dependenceDOS = dependenceMapper.selectList(new LambdaQueryWrapper<DependenceDO>().in(DependenceDO::getDependMid, allMid));
        if (CollectionUtils.isEmpty(dependenceDOS)) {
            groupMapper.deleteById(id);
            return Response.buildSuccess();
        }
        return Response.buildFailure(ErrorCodeEnum.HAVE_DEPEND_GROUP_NOT_ALLOW_DELETE.getCode(), ErrorCodeEnum.HAVE_DEPEND_GROUP_NOT_ALLOW_DELETE.getMsg());
    }

}
