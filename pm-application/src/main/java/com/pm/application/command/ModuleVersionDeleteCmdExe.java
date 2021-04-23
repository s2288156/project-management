package com.pm.application.command;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.pm.infrastructure.mapper.ModuleVersionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wcy
 */
@Component
public class ModuleVersionDeleteCmdExe {

    @Autowired
    private ModuleVersionMapper moduleVersionMapper;

    public void execute(String projectId) {
        // 根据项目id查询出来自己模块id的所有版本id
        List<String> moduleVersionIdList = moduleVersionMapper.selectModuleVersionIdByProjectId(projectId);
        if (CollectionUtils.isNotEmpty(moduleVersionIdList)) {
            moduleVersionMapper.deleteBatchIds(moduleVersionIdList);
        }
    }
}
