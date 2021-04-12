package com.pm.infrastructure.dataobject;

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
@TableName(autoResultMap = true, value = "t_dependence")
public class DependenceDO {

    private String id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /**
     * 项目id
     **/
    private String pid;

    /**
     * 当前模块依赖其它模块id
     **/
    private String dependMid;

    /**
     * 依赖模块信息
     **/
    private String dependModuleInfo;

}
