package com.pm.demo;

import com.pm.application.convertor.GroupConvertor;
import com.pm.application.dto.cmd.GroupAddCmd;
import com.pm.infrastructure.dataobject.GroupDO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StopWatch;

/**
 * @author wcy
 */
@Slf4j
public class MapstructPerformanceTest {


    @ValueSource(ints = {1, 1, 10, 10000, 100000, 1000000})
    @ParameterizedTest
    void testMapstructVSBeanUtils(int loopsNum) {
        log.warn("遍历次数loopsNum = {}", loopsNum);
        GroupAddCmd groupAddCmd = new GroupAddCmd();
        groupAddCmd.setName("hahah");

        StopWatch mapstructWatch = new StopWatch("mapstruct");
        StopWatch beanUtilsWatch = new StopWatch("beanUtils");

        beanUtilsWatch.start();
        beanUtilsConvertor(groupAddCmd, loopsNum);
        beanUtilsWatch.stop();

        log.warn("beanUtils = {}", beanUtilsWatch.getTotalTimeNanos());

        mapstructWatch.start();
        mapstructConvertor(groupAddCmd, loopsNum);
        mapstructWatch.stop();

        log.warn("mapstruct = {}", mapstructWatch.getTotalTimeNanos());
    }

    private void mapstructConvertor(GroupAddCmd groupAddCmd, int loopsNum) {
        for (int i = 0; i < loopsNum; i++) {
            GroupDO groupDO = GroupConvertor.INSTANCE.convert2Do(groupAddCmd);
        }
    }

    private void beanUtilsConvertor(GroupAddCmd groupAddCmd, int loopsNum) {
        for (int i = 0; i < loopsNum; i++) {
            GroupDO groupDO = new GroupDO();
            BeanUtils.copyProperties(groupAddCmd, groupDO);
        }
    }
}
