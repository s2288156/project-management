package com.pm.domain.project;

import java.util.List;

/**
 * @author wcy
 */
public class ProjectModule {

    private String id;

    private String name;

    private String version;

    private Boolean isLatest;

    private List<ProjectModule> dependencyProjectModule;
}
