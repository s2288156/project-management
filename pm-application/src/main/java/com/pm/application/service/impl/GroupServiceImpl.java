package com.pm.application.service.impl;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pm.application.execute.command.GroupDeleteExe;
import com.pm.infrastructure.consts.ErrorCodeEnum;
import com.pm.application.convertor.GroupConvertor;
import com.pm.application.dto.cmd.GroupAddCmd;
import com.pm.application.dto.vo.GroupVO;
import com.pm.application.service.IGroupService;
import com.pm.infrastructure.dataobject.GroupDO;
import com.pm.infrastructure.entity.PageQuery;
import com.pm.infrastructure.entity.PageResponse;
import com.pm.infrastructure.mapper.GroupMapper;
import com.zyzh.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private GroupDeleteExe groupDeleteExe;

    @Override
    public SingleResponse<String> addGroup(GroupAddCmd addCmd) {
        Optional<GroupDO> optional = groupMapper.selectByName(addCmd.getName());
        if (optional.isPresent()) {
            throw new BizException(ErrorCodeEnum.GROUP_NAME_EXISTED);
        }
        GroupDO groupDO = GroupConvertor.INSTANCE.convert2Do(addCmd);
        groupMapper.insert(groupDO);
        return SingleResponse.of(groupDO.getId());
    }

    @Override
    public PageResponse<GroupVO> listGroup(PageQuery pageQuery) {
        Page<GroupDO> page = pageQuery.createPage();
        groupMapper.selectPage(page, new LambdaQueryWrapper<>());

        List<GroupVO> datas = page.getRecords()
                .stream()
                .map(GroupConvertor.INSTANCE::convertDo2Vo)
                .collect(Collectors.toList());

        return PageResponse.of(datas, page.getTotal());
    }

    @Override
    public Response deleteById(String id) {
        return groupDeleteExe.execute(id);
    }

}
