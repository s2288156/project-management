package com.pm.application.command;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.pm.infrastructure.mapper.DependenceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wcy
 */
@Component
public class DependenceDeleteCmdExe {
    @Autowired
    private DependenceMapper dependenceMapper;

    public void execute(String projectId) {
        // 根据项目id查询出自己项目引用自己模块的DependenceId
        List<String> dependIdList = dependenceMapper.queryDependIdByProjectId(projectId);
        if (CollectionUtils.isNotEmpty(dependIdList)) {
            dependenceMapper.deleteBatchIds(dependIdList);
        }
    }
}
