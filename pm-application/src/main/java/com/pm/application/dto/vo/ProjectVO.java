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
public class ProjectVO extends DTO {

    private String id;

    private LocalDateTime createTime;

    private String groupId;

    private String groupName;

    /**
     * 项目名
     **/
    private String name;

    /**
     * 描述
     **/
    private String description;

}
