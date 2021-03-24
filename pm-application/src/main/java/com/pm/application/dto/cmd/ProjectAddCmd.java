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
public class ProjectAddCmd extends Command {

    @NotBlank(message = "参数错误")
    private String groupId;

    @NotBlank(message = "名称不能为空")
    private String name;

    /**
     * 描述
     **/
    private String desc;
}
