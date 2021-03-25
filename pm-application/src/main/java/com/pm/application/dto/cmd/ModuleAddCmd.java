package com.pm.application.dto.cmd;

import com.alibaba.cola.dto.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wcy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ModuleAddCmd extends Command {

    /**
     * 所属项目id
     */
    private String pid;

    /**
     * 模块名称
     **/
    private String name;

    /**
     * 默认版本号
     */
    private String version;
}
