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
public class ModuleDeleteCmd extends Command {

    /**
     * 模块id
     */
    @NotBlank(message = "模块ID必传")
    private String id;

}
