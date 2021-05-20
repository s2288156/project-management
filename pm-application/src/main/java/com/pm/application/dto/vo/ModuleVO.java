package com.pm.application.dto.vo;

import com.alibaba.cola.dto.DTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author wcy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ModuleVO extends DTO {

    private String id;

    private String projectName;

    /**
     * moduleName
     */
    private String name;

    /**
     * moduleVersion
     */
    private String latestVersion;

    private String description;

    private LocalDateTime createTime;

    public static ModuleVO createForId(String id) {
        ModuleVO moduleVO = new ModuleVO();
        moduleVO.setId(id);
        return moduleVO;
    }

}
