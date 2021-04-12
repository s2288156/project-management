package com.pm.application.dto.vo;

import com.alibaba.cola.dto.DTO;
import com.pm.infrastructure.dataobject.GroupDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

/**
 * @author wcy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GroupVO extends DTO {

    private String id;

    private String name;

    private LocalDateTime createTime;

    public static GroupVO convertForDo(GroupDO groupDO) {
        GroupVO groupVO = new GroupVO();
        BeanUtils.copyProperties(groupDO, groupVO);
        return groupVO;
    }
}
