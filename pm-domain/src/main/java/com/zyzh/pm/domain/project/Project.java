package com.zyzh.pm.domain.project;

import java.util.List;

/**
 * @author wcy
 */
public class Project {

    private String id;

    private String name;

    private String description;

    private ProjectGroup group;

    private List<ProjectModule> projectModules;
}
