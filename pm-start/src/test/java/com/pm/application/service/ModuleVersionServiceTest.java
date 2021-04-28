package com.pm.application.service;

import com.alibaba.cola.dto.Response;
import com.pm.NoneWebBaseTest;
import com.pm.application.consts.ErrorCodeEnum;
import com.pm.application.dto.cmd.ModuleVersionDeleteCmd;
import com.pm.application.service.impl.ModuleServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Administrator
 * @ClassName ModuleVersionServiceTest
 * @date 2021/4/27 0027 11:53
 **/

public class ModuleVersionServiceTest extends NoneWebBaseTest {

    @Autowired
    private ModuleServiceImpl moduleServiceImpl;


    @Transactional
    @Test
    void deleteModelVersionOne(){
        //模块版本未被引用,但为最新版本
        ModuleVersionDeleteCmd moduleVersionDeleteCmd = new ModuleVersionDeleteCmd();
        moduleVersionDeleteCmd.setId("1384046747454271490");
        moduleVersionDeleteCmd.setMid("1381488489669283841");
        moduleVersionDeleteCmd.setVersion("1.0.0");
        Response response = moduleServiceImpl.deleteModuleVersion(moduleVersionDeleteCmd);
        Assertions.assertFalse(response.isSuccess());
        assertEquals(ErrorCodeEnum.LATEST_MODULE_VERSION_NOT_ALLOW_DELETE.getErrorCode(),response.getErrCode());
    }

    @Transactional
    @Test
    void deleteModelVersionTwo(){
        //模块版本被引用,不是最新版本
        ModuleVersionDeleteCmd moduleVersionDeleteCmd = new ModuleVersionDeleteCmd();
        moduleVersionDeleteCmd.setId("1382572723725389827");
        moduleVersionDeleteCmd.setMid("1382571870658482179");
        moduleVersionDeleteCmd.setVersion("1.0.2");
        Response response = moduleServiceImpl.deleteModuleVersion(moduleVersionDeleteCmd);
        Assertions.assertFalse(response.isSuccess());
        assertEquals(ErrorCodeEnum.MODULE_DEPEND_NOT_ALLOW_DEL.getErrorCode(),response.getErrCode());
    }

    @Transactional
    @Test
    void deleteModelVersionThree(){
        //模块版本被引用,最新版本
        ModuleVersionDeleteCmd moduleVersionDeleteCmd = new ModuleVersionDeleteCmd();
        moduleVersionDeleteCmd.setId("1386152025532366850");
        moduleVersionDeleteCmd.setMid("1382538753482682369");
        moduleVersionDeleteCmd.setVersion("1.0.2");
        Response response = moduleServiceImpl.deleteModuleVersion(moduleVersionDeleteCmd);
        Assertions.assertFalse(response.isSuccess());
        assertEquals(ErrorCodeEnum.LATEST_MODULE_VERSION_NOT_ALLOW_DELETE.getErrorCode(),response.getErrCode());
    }

    @Transactional
    @Test
    void deleteModelVersioFour(){
        //模块版本未被引用,不新版本
        ModuleVersionDeleteCmd moduleVersionDeleteCmd = new ModuleVersionDeleteCmd();
        moduleVersionDeleteCmd.setId("1384048487826190337");
        moduleVersionDeleteCmd.setMid("1381488489669283841");
        moduleVersionDeleteCmd.setVersion("1.0.2");
        Response response = moduleServiceImpl.deleteModuleVersion(moduleVersionDeleteCmd);
        Assertions.assertTrue(response.isSuccess());
    }
}
