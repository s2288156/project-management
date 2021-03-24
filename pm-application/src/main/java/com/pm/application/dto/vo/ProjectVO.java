package com.pm.application.dto.vo;

import com.alibaba.cola.dto.DTO;
import com.pm.infrastructure.dataobject.ProjectDO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

/**
 * @author wcy
 */
@Data
public class ProjectVO extends DTO {

    private String id;

    private LocalDateTime createTime;

    private String groupId;

    /**
     * 项目名
     **/
    private String name;

    /**
     * 描述
     **/
    private String description;

    public static ProjectVO convert2DO(ProjectDO projectDO) {
        ProjectVO projectVO = new ProjectVO();
        BeanUtils.copyProperties(projectDO, projectVO);
        return projectVO;
    }
}
