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
public class ProjectDependAddCmd extends Command {

    @NotBlank
    private String pid;

    @NotBlank
    private String mid;

    @NotBlank
    private String version;

    private String description;
}
