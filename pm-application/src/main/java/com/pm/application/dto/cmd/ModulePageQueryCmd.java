package com.pm.application.dto.cmd;

import com.pm.infrastructure.entity.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wcy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ModulePageQueryCmd extends PageQuery {

    private String pid;
}
