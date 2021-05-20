package com.pm.application.execute.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pm.application.convertor.ModuleVersionConvertor;
import com.pm.application.dto.query.ModuleVersionPageQuery;
import com.pm.application.dto.vo.ModuleVersionVO;
import com.pm.infrastructure.dataobject.ModuleDO;
import com.pm.infrastructure.dataobject.ModuleVersionDO;
import com.pm.infrastructure.entity.PageResponse;
import com.pm.infrastructure.mapper.ModuleMapper;
import com.pm.infrastructure.mapper.ModuleVersionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wcy
 */
@Component
public class ModuleVersionPageQueryExe {

    @Autowired
    private ModuleVersionMapper moduleVersionMapper;

    @Autowired
    private ModuleMapper moduleMapper;

    public PageResponse<ModuleVersionVO> execute(ModuleVersionPageQuery versionPageQueryCmd) {
        ModuleDO moduleDO = moduleMapper.selectById(versionPageQueryCmd.getMid());

        Page<ModuleVersionDO> page = versionPageQueryCmd.createPage();
        moduleVersionMapper.listModuleVersion(page, versionPageQueryCmd.getMid());
        List<ModuleVersionVO> moduleVersionVOList = page.getRecords().stream()
                .map(moduleVersionDO -> ModuleVersionVO.convertForDo(moduleVersionDO, moduleDO.getLatestVersion()))
                .collect(Collectors.toList());
        return PageResponse.of(moduleVersionVOList, page.getTotal());
    }
}
