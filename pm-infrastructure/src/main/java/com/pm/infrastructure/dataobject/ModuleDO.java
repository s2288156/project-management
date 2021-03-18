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

}
