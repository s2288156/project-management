package com.pm.controller;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.pm.application.dto.cmd.GroupAddCmd;
import com.pm.application.dto.vo.GroupVO;
import com.pm.application.service.IGroupService;
import com.pm.infrastructure.entity.PageQuery;
import com.pm.infrastructure.entity.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        return groupService.addGroup(addCmd);
    }

    @GetMapping("/list")
    public PageResponse<GroupVO> listGroup(PageQuery pageQuery) {
        return groupService.listGroup(pageQuery);
    }

    @DeleteMapping("/{id}")
    public Response delete(@PathVariable String id) {
        return groupService.deleteById(id);
    }
}
