package com.pm.domain.project;

import com.alibaba.cola.dto.DTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author wcy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Project extends DTO {

    private String id;

    private String name;

    private String description;

    private ProjectGroup group;

    private List<ProjectModule> projectModules;
}
