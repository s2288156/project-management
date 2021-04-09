package com.zyzh.pm.domain.project;

import com.alibaba.cola.dto.DTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author wcy
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class DependModuleInfo extends DTO {

    @JsonIgnoreProperties
    private String id;

    private String projectName;

    private String moduleName;

    private String version;

    private String description;

}
