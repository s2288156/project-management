package com.pm.application.execute.command;

import com.alibaba.cola.dto.SingleResponse;
import com.pm.application.convertor.ModuleConvertor;
import com.pm.application.dto.cmd.ModuleAddCmd;
import com.pm.application.dto.vo.ModuleVO;
import com.pm.infrastructure.consts.ErrorCodeEnum;
import com.pm.infrastructure.dataobject.ModuleDO;
import com.pm.infrastructure.dataobject.ModuleVersionDO;
import com.pm.infrastructure.mapper.ModuleMapper;
import com.pm.infrastructure.mapper.ModuleVersionMapper;
import com.zyzh.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author wcy
 */
@Component
public class ModuleAddCmdExe {

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private ModuleVersionMapper moduleVersionMapper;

    public SingleResponse<ModuleVO> execute(ModuleAddCmd addCmd) {
        checkModule(addCmd.getName());
        ModuleDO moduleDO = ModuleConvertor.INSTANCE.convert2Do(addCmd);
        moduleMapper.insert(moduleDO);
        insertModule(addCmd, moduleDO);
        return SingleResponse.of(ModuleVO.createForId(moduleDO.getId()));
    }

    private void insertModule(ModuleAddCmd addCmd, ModuleDO moduleDO) {
        ModuleVersionDO moduleVersionDO = new ModuleVersionDO();
        moduleVersionDO.setMid(moduleDO.getId());
        moduleVersionDO.setVersion(addCmd.getVersion());
        moduleVersionDO.setDescription(addCmd.getDescription());
        moduleVersionMapper.insert(moduleVersionDO);
    }

    private void checkModule(String moduleName) {
        Optional<ModuleDO> moduleOptional = moduleMapper.selectByName(moduleName);
        if (moduleOptional.isPresent()) {
            throw new BizException(ErrorCodeEnum.MODULE_NAME_EXISTED);
        }
    }
}
