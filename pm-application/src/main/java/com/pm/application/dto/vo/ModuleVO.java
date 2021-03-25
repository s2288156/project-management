package com.pm.application.dto.vo;

import com.alibaba.cola.dto.DTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wcy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ModuleVO extends DTO {

    private String id;

    public static ModuleVO createForId(String id) {
        ModuleVO moduleVO = new ModuleVO();
        moduleVO.setId(id);
        return moduleVO;
    }
}
