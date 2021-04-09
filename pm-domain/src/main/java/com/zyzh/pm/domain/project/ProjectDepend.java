package com.zyzh.pm.domain.project;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wcy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProjectDepend extends Project{

    private DependModuleInfo dependModuleInfo;
}
