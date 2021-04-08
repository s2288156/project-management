package com.pm.application.dto.vo;

import com.alibaba.cola.dto.DTO;
import com.pm.infrastructure.dataobject.ModuleDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

/**
 * @author wcy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DependModuleVO extends DTO {

    private String id;

    private String projectName;

    /**
     * moduleName
     */
    private String moduleName;

    /**
     * moduleVersion
     */
    private String moduleVersion;

    private String moduleDescription;

}
