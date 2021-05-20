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
public class ModuleUpdateLatestVersionCmd extends Command {

    @NotBlank(message = "模块Id不能为空")
    private String id;

    @NotBlank
    private String latestVersion;
}
