package com.zyzh.pm.domain.project;

import java.util.List;

/**
 * @author wcy
 */
public class Module {

    private String id;

    private String name;

    private String version;

    private Boolean isLatest;

    private List<Module> dependencyModule;
}
