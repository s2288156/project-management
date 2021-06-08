package com.pm.application.dto.vo;

import com.alibaba.cola.dto.DTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wcy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ResourceVO extends DTO {
    private String id;

    private String url;
}
