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
public class GroupAddCmd extends Command {

    @NotBlank(message = "名称不能为空")
    private String name;
}
