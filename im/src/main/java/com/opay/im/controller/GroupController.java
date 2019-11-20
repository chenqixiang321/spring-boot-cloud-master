package com.opay.im.controller;

import com.opay.im.model.Group;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/group")
@Api(value = "聊天群组功能API")
public class GroupController {

    @ApiOperation(value = "获取群组信息", notes = "获取群组信息")
    @GetMapping("{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "群组ID", required = true, paramType = "path", dataType = "String")
    })
    public Group getGroupInfo(@PathVariable String id) {
        return new Group(id, "","","",100);
    }
}
