package com.pm.application.dto.cmd;

import com.alibaba.cola.dto.Command;
import com.pm.infrastructure.dataobject.RoleResourceDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<RoleResourceDO> convert2DoList() {
        return resourceIds.stream()
                .map(resourceId -> {
                    RoleResourceDO roleResourceDO = new RoleResourceDO();
                    roleResourceDO.setResourceId(resourceId);
                    roleResourceDO.setRoleId(this.roleId);
                    return roleResourceDO;
                }).collect(Collectors.toList());
    }
}
