package com.pm.application.dto.cmd;

import com.alibaba.cola.dto.Command;
import com.pm.infrastructure.dataobject.UserRoleDO;
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
public class UserSetRolesCmd extends Command {

    @NotBlank
    private String uid;

    private List<String> roleIds;

    public List<UserRoleDO> convert2UserRoleDo() {
        return this.roleIds
                .stream()
                .map(roleId -> {
                    UserRoleDO userRoleDO = new UserRoleDO();
                    userRoleDO.setUid(this.uid);
                    userRoleDO.setRoleId(roleId);
                    return userRoleDO;
                })
                .collect(Collectors.toList());
    }
}
