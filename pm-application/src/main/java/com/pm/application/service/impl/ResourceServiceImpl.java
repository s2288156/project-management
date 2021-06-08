package com.pm.application.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pm.application.convertor.ResourceConvertor;
import com.pm.application.dto.cmd.ResourceAddCmd;
import com.pm.application.dto.query.RoleResourcePageQuery;
import com.pm.application.dto.vo.ResourceVO;
import com.pm.application.execute.command.ResourceAddCmdExe;
import com.pm.application.execute.command.ResourceDeleteCmdExe;
import com.pm.application.service.IResourceService;
import com.pm.infrastructure.dataobject.ResourceDO;
import com.pm.infrastructure.entity.PageQuery;
import com.pm.infrastructure.entity.PageResponse;
import com.pm.infrastructure.mapper.ResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wcy
 */
@Service
public class ResourceServiceImpl implements IResourceService {
    @Autowired
    private ResourceAddCmdExe resourceAddCmdExe;

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private ResourceDeleteCmdExe resourceDeleteCmdExe;

    @Override
    public String addResource(ResourceAddCmd resourceAddCmd) {
        return resourceAddCmdExe.execute(resourceAddCmd);
    }

    @Override
    public PageResponse<ResourceVO> pageResource(PageQuery pageQuery) {
        Page<ResourceDO> page = pageQuery.createPage();
        Page<ResourceDO> resourcePage = resourceMapper.selectPage(page, null);
        List<ResourceVO> voList = resourcePage.getRecords()
                .stream()
                .map(ResourceConvertor.INSTANCE::do2Vo)
                .collect(Collectors.toList());
        return PageResponse.of(voList, resourcePage.getTotal());
    }

    @Override
    public PageResponse<ResourceVO> pageRoleResource(RoleResourcePageQuery pageQuery) {
        Page<ResourceDO> page = pageQuery.createPage();
        Page<ResourceDO> resourcePage = resourceMapper.selectAllByRoleId(page, pageQuery.getRoleId());
        List<ResourceVO> voList = resourcePage.getRecords()
                .stream()
                .map(ResourceConvertor.INSTANCE::do2Vo)
                .collect(Collectors.toList());
        return PageResponse.of(voList, resourcePage.getTotal());
    }

    @Override
    public void deleteById(String id) {
        resourceDeleteCmdExe.execute(id);
    }

}
