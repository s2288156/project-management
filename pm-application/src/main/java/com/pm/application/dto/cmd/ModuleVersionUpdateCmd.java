package com.pm.application.dto.cmd;

import com.alibaba.cola.dto.Command;
import com.pm.infrastructure.dataobject.ModuleVersionDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;

/**
 * @author wcy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ModuleVersionUpdateCmd extends Command {

    @NotBlank
    private String id;

    @NotBlank
    private String description;

    public ModuleVersionDO convert2Do() {
        ModuleVersionDO moduleVersionDO = new ModuleVersionDO();
        BeanUtils.copyProperties(this, moduleVersionDO);
        return moduleVersionDO;
    }
}
