package com.pm.application.dto.vo;

import com.alibaba.cola.dto.DTO;
import com.pm.infrastructure.dataobject.ModuleVersionDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

/**
 * @author wcy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ModuleVersionVO extends DTO {

    private String id;

    /**
     * version
     */
    private String version;

    private String description;

    private LocalDateTime createTime;

    public static ModuleVersionVO convertForDo(ModuleVersionDO moduleVersionDO) {
        ModuleVersionVO moduleVersionVO = new ModuleVersionVO();
        BeanUtils.copyProperties(moduleVersionDO, moduleVersionVO);
        return moduleVersionVO;
    }
}
