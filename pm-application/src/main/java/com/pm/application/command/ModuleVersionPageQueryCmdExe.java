package com.pm.application.command;

import com.pm.application.dto.cmd.ModuleVersionPageQueryCmd;
import com.pm.application.dto.vo.ModuleVersionVO;
import com.pm.infrastructure.entity.PageResponse;
import com.pm.infrastructure.mapper.ModuleMapper;
import com.pm.infrastructure.mapper.ModuleVersionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wcy
 */
@Component
public class ModuleVersionPageQueryCmdExe {

    @Autowired
    private ModuleVersionMapper moduleVersionMapper;

    @Autowired
    private ModuleMapper moduleMapper;

    public PageResponse<ModuleVersionVO> execute(ModuleVersionPageQueryCmd versionPageQueryCmd) {
        return null;
    }
}
