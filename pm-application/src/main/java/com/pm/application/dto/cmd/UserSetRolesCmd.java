package com.pm.application.dto.cmd;

import com.alibaba.cola.dto.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author wcy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserSetRolesCmd extends Command {

    @NotNull
    private List<String> roleIds;

}
