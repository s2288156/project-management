package com.pm.application.dto.vo;

import com.alibaba.cola.dto.DTO;
import com.pm.application.convertor.ModuleVersionConvertor;
import com.pm.infrastructure.dataobject.ModuleVersionDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

/**
 * @author wcy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ModuleVersionVO extends DTO {

    private String id;

    private String mid;

    /**
     * version
     */
    private String version;

    private String description;

    private LocalDateTime createTime;

    private Boolean isLatest;

    public ModuleVersionVO() {
        // 默认不是最新版本
        this.isLatest = false;
    }

    public static ModuleVersionVO convertForDo(ModuleVersionDO moduleVersionDO, String latestVersion) {
        ModuleVersionVO moduleVersionVO = ModuleVersionConvertor.INSTANCE.convert2Vo(moduleVersionDO);
        if (StringUtils.equals(moduleVersionDO.getVersion(), latestVersion)) {
            moduleVersionVO.setIsLatest(true);
        }
        return moduleVersionVO;
    }
}
