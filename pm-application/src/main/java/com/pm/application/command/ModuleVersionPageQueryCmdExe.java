package com.pm.application.command;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pm.application.dto.cmd.ModuleVersionPageQueryCmd;
import com.pm.application.dto.vo.ModuleVersionVO;
import com.pm.infrastructure.dataobject.ModuleVersionDO;
import com.pm.infrastructure.entity.PageResponse;
import com.pm.infrastructure.mapper.ModuleVersionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wcy
 */
@Component
public class ModuleVersionPageQueryCmdExe {

    @Autowired
    private ModuleVersionMapper moduleVersionMapper;

    public PageResponse<ModuleVersionVO> execute(ModuleVersionPageQueryCmd versionPageQueryCmd) {
        Page<ModuleVersionDO> page = versionPageQueryCmd.createPage();
        moduleVersionMapper.listModuleVersion(page, versionPageQueryCmd.getMid());
        List<ModuleVersionVO> moduleVersionVOList = page.getRecords().stream()
                .map(ModuleVersionVO::convertForDo)
                .collect(Collectors.toList());
        return PageResponse.of(moduleVersionVOList, page.getTotal());
    }
}
