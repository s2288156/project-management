package com.pm.application.dto.query;

import com.pm.infrastructure.entity.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wcy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserRolesQuery extends PageQuery {

    private String uid;

}
