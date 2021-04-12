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
@TableName(autoResultMap = true, value = "t_module_version")
public class ModuleVersionDO {

    private String id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /**
     * module_id
     **/
    private String mid;

    /**
     * 版本
     **/
    private String version;

    /**
     * 版本描述
     **/
    private String description;

}
