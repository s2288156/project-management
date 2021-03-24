package com.pm.infrastructure.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author wcy-auto
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName(autoResultMap = true, value = "t_project")
public class ProjectDO {

    private String id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String groupId;

    /**
     * 项目名
     **/
    private String name;

    /**
     * 描述
     **/
    private String description;

    @TableField(exist = false)
    private String groupName;
}
