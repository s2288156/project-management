package com.pm.application.service;

import com.alibaba.cola.dto.Response;
import com.pm.NoneWebBaseTest;
import com.pm.infrastructure.consts.ErrorCodeEnum;
import com.pm.application.dto.cmd.UserRegisterCmd;
import com.pm.application.dto.vo.UserVO;
import com.pm.infrastructure.entity.PageQuery;
import com.pm.infrastructure.entity.PageResponse;
import com.zyzh.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wcy
 */
@Transactional
@Slf4j
public class UserServiceTest extends NoneWebBaseTest {

    @Autowired
    private IUserService userService;

    UserRegisterCmd userRegisterCmd = new UserRegisterCmd();

    PageQuery pageQuery = new PageQuery();

    @BeforeEach
    void setUp() {
        userRegisterCmd.setUsername("test-go-go");
    }

    @Test
    void testSuccessRegister() {
        userRegisterCmd.setPassword("112233");
        userRegisterCmd.setConfirmPassword("112233");
        Response response = userService.userRegister(userRegisterCmd);
        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void testFailForPwdConfirm() {
        userRegisterCmd.setPassword("112233");
        userRegisterCmd.setConfirmPassword("11223344");
        BizException bizException = Assertions.assertThrows(BizException.class, () -> userService.userRegister(userRegisterCmd));
        Assertions.assertEquals(ErrorCodeEnum.TWO_PASSWORD_ENTERED_NOT_SAME.getErrorCode(), bizException.getErrCode());
    }

    @Test
    void testUserList() {
        PageResponse<UserVO> response = userService.listUser(pageQuery);
        Assertions.assertTrue(response.isSuccess());
        log.info("{}", response.getData());
    }
}
