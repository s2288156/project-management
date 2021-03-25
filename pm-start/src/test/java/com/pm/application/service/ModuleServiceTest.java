package com.pm.application.service;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pm.NoneWebBaseTest;
import com.pm.application.consts.ErrorCodeEnum;
import com.pm.application.dto.cmd.ModuleAddCmd;
import com.pm.application.dto.vo.ModuleVO;
import com.pm.infrastructure.dataobject.ModuleDO;
import com.pm.infrastructure.dataobject.ModuleVersionDO;
import com.pm.infrastructure.dataobject.ProjectDO;
import com.pm.infrastructure.mapper.ModuleMapper;
import com.pm.infrastructure.mapper.ModuleVersionMapper;
import com.pm.infrastructure.mapper.ProjectMapper;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author wcy
 */
public class ModuleServiceTest extends NoneWebBaseTest {

    @Autowired
    private IModuleService moduleService;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private ModuleVersionMapper moduleVersionMapper;

    @Transactional
    @Test
    void addOneModuleTest() {
        ModuleAddCmd moduleAddCmd = new ModuleAddCmd();

        ProjectDO projectDO = new ProjectDO();
        projectDO.setName("testaaaaa");
        projectMapper.insert(projectDO);

        moduleAddCmd.setPid(projectDO.getId());
        moduleAddCmd.setName("testModule");
        moduleAddCmd.setVersion("1.0.0");

        SingleResponse<ModuleVO> singleResponse = moduleService.addOne(moduleAddCmd);

        assertTrue(singleResponse.isSuccess());

        SingleResponse<ModuleVO> singleResponse1 = moduleService.addOne(moduleAddCmd);

        assertFalse(singleResponse1.isSuccess());
        assertEquals(ErrorCodeEnum.MODULE_NAME_EXISTED.getErrorCode(), singleResponse1.getErrCode());

        Optional<ModuleDO> moduleOptional = moduleMapper.selectByName(moduleAddCmd.getName());
        // 第二次插入将失败
        assertFalse(moduleOptional.isPresent());

        ModuleVersionDO moduleVersionDO = moduleVersionMapper.selectOne(new LambdaQueryWrapper<ModuleVersionDO>()
                .eq(ModuleVersionDO::getMid, singleResponse.getData().getId())
        );
        assertNull(moduleVersionDO);
    }
}
