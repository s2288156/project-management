package com.pm.application.execute.command;

import com.alibaba.cola.dto.Response;
import com.pm.application.dto.cmd.ModuleVersionDeleteCmd;
import com.pm.infrastructure.consts.ErrorCodeEnum;
import com.pm.infrastructure.dataobject.DependenceDO;
import com.pm.infrastructure.dataobject.ModuleDO;
import com.pm.infrastructure.mapper.DependenceMapper;
import com.pm.infrastructure.mapper.ModuleMapper;
import com.pm.infrastructure.mapper.ModuleVersionMapper;
import com.pm.infrastructure.tool.JsonUtils;
import com.zyzh.exception.BizException;
import com.zyzh.pm.domain.project.DependModuleInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author Administrator @ClassName ModuleVersionDeleteCmdExe
 * @date 2021/4/19 0019 09:58
 **/
@Component
public class ModuleVersionDeleteCmdExe {

    @Autowired
    private DependenceMapper dependenceMapper;

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private ModuleVersionMapper moduleVersionMapper;

    public Response execute(ModuleVersionDeleteCmd moduleVersionDeleteCmd) {
        ModuleDO moduleDO = moduleMapper.selectById(moduleVersionDeleteCmd.getMid());

        deleteVersionLatestCheck(moduleVersionDeleteCmd.getVersion(), moduleDO.getLatestVersion());

        deleteVersionDependCheck(moduleVersionDeleteCmd.getVersion(), moduleVersionDeleteCmd.getMid());

        moduleVersionMapper.deleteById(moduleVersionDeleteCmd.getId());
        return Response.buildSuccess();
    }

    /**
     * 最新的module version不允许删除
     *
     * @param deleteVersion 要删除的版本号
     * @param moduleVersion module最新版本号
     */
    private void deleteVersionLatestCheck(String deleteVersion, String moduleVersion) {
        if (StringUtils.equals(moduleVersion, deleteVersion)) {
            throw new BizException(ErrorCodeEnum.LATEST_MODULE_VERSION_NOT_ALLOW_DELETE);
        }
    }

    /**
     * 待删除module version是否被依赖检查
     *
     * @param deleteVersion 待删除module version
     * @param dependMid     待删除moduleId
     */
    private void deleteVersionDependCheck(String deleteVersion, String dependMid) {
        List<DependenceDO> dependenceDOList = dependenceMapper.selectByDependMid(dependMid);
        if (CollectionUtils.isEmpty(dependenceDOList)) {
            return;
        }
        for (DependenceDO dependenceDO : dependenceDOList) {
            DependModuleInfo dependModuleInfo = JsonUtils.fromJson(dependenceDO.getDependModuleInfo(), DependModuleInfo.class);
            if (StringUtils.equals(deleteVersion, dependModuleInfo.getVersion())) {
                throw new BizException(ErrorCodeEnum.MODULE_DEPEND_NOT_ALLOW_DEL);
            }
        }
    }
}
