package com.pm.application.command;

import com.alibaba.cola.dto.SingleResponse;
import com.pm.application.consts.ErrorCodeEnum;
import com.pm.application.convertor.ModuleConvertor;
import com.pm.application.dto.cmd.ModuleAddCmd;
import com.pm.application.dto.vo.ModuleVO;
import com.pm.infrastructure.dataobject.ModuleDO;
import com.pm.infrastructure.mapper.ModuleMapper;
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

    public SingleResponse<ModuleVO> execute(ModuleAddCmd addCmd) {

        ModuleDO moduleDO = ModuleConvertor.convert2Do(addCmd);
        moduleMapper.insert(moduleDO);

        return SingleResponse.of(ModuleVO.createForId(moduleDO.getId()));
    }
}
