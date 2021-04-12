package com.pm.application.dto;

import com.pm.infrastructure.entity.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author wcy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PidQuery extends PageQuery {

    @NotBlank
    private String pid;
}
