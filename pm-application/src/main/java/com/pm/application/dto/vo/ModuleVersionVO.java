package com.pm.application.dto.vo;

import com.alibaba.cola.dto.DTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author wcy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ModuleVersionVO extends DTO {

    private String id;

    /**
     * version
     */
    private String version;

    private String description;

    private LocalDateTime createTime;

}
