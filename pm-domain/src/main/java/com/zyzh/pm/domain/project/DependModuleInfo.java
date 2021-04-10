package com.zyzh.pm.domain.project;

import com.alibaba.cola.dto.DTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wcy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DependModuleInfo extends DTO {

    private String projectName;

    private String moduleName;

    private String version;

}
