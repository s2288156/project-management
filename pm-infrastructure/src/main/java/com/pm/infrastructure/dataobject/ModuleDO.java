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
@TableName(autoResultMap = true, value = "t_module")
public class ModuleDO {

    private String id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /**
     * 项目id
     **/
    private String pid;

    /**
     * 模块名称
     **/
    private String name;

    /**
     * 模块说明
     **/
    private String description;

    /**
     * 是否对外开发，0 - 不开放，1 - 开放
     **/
    private Integer openingUp;

    /**
     * 最新版本
     **/
    private String latestVersion;

    /**
     * moduleVersion
     */
    @TableField(exist = false)
    private String version;

    @TableField(exist = false)
    private String projectName;
}
