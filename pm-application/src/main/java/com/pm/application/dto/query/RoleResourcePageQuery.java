package com.pm.application.dto.query;

import com.pm.infrastructure.entity.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author wcy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleResourcePageQuery extends PageQuery {

    @NotBlank
    private String roleId;

}
