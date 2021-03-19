package com.pm.controller;

import com.alibaba.cola.dto.SingleResponse;
import com.pm.application.dto.cmd.GroupAddCmd;
import com.pm.application.service.IGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wcy
 */
@RequestMapping("/group")
@RestController
public class GroupController {

    @Autowired
    private IGroupService groupService;

    @PostMapping("/add")
    public SingleResponse<?> addGroup(@RequestBody @Validated GroupAddCmd addCmd) {

        return SingleResponse.buildSuccess();
    }
}
