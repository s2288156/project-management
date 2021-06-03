package com.pm.application.dto.cmd;

import com.alibaba.cola.dto.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author wcy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ResourceAddCmd extends Command {

    /**
     * {@link org.springframework.web.bind.annotation.RequestMethod}
     */
    @NotBlank
    private String requestMethod;

    @NotBlank
    private String apiPath;
}
