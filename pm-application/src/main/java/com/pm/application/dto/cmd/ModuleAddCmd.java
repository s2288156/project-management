package com.pm.application.dto.cmd;

import com.alibaba.cola.dto.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author wcy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ModuleAddCmd extends Command {

    /**
     * 所属项目id
     */
    @NotBlank(message = "项目id必传")
    private String pid;

    /**
     * 模块名称
     **/
    @NotBlank(message = "模块名称必传")
    private String name;

    /**
     * 默认版本号
     */
    @NotBlank(message = "版本号必传")
    private String version;
}
