package com.pm.application.dto.cmd;

import com.alibaba.cola.dto.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Administrator
 * @ClassName ModuleVersionDeleteCmd
 * @date 2021/4/16 0016 08:32
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class ModuleVersionDeleteCmd extends Command {

    private String id;

    private String mid;

    private String version;
}
