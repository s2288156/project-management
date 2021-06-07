package com.pm.application.dto.cmd;

import com.alibaba.cola.dto.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author wcy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleSetResourcesCmd extends Command {

    @NotBlank
    private String roleId;

    @NotNull
    private List<String> resourceIds;
}
