package com.opay.im.controller;

import com.opay.im.model.GroupModel;
import com.opay.im.model.request.CreateGroupRequest;
import com.opay.im.model.request.InviteGroupRequest;
import com.opay.im.model.request.QuitGroupRequest;
import com.opay.im.model.request.RemoveGroupMemberRequest;
import com.opay.im.model.request.UpdateGroupModelRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public GroupModel getGroupInfo(@PathVariable String id) {
        return new GroupModel(id, "","","",100);
    }

    @ApiOperation(value = "建群", notes = "建群")
    @PostMapping("/create")
    public CreateGroupRequest createGroup(@RequestBody @ApiParam(name = "建群参数", value = "传入json格式", required = true) CreateGroupRequest createGroupRequest) {
        return createGroupRequest;
    }

    @ApiOperation(value = "修改群信息", notes = "修改群信息")
    @PostMapping("/update")
    public UpdateGroupModelRequest updateGroup(@RequestBody @ApiParam(name = "修改群信息参数", value = "传入json格式", required = true) UpdateGroupModelRequest updateGroupModelRequest) {
        return updateGroupModelRequest;
    }

    @ApiOperation(value = "邀请入群", notes = "邀请入群")
    @PostMapping("/invite")
    public InviteGroupRequest inviteGroup(@RequestBody @ApiParam(name = "邀请入群参数", value = "传入json格式", required = true) InviteGroupRequest inviteGroupRequest) {
        return inviteGroupRequest;
    }

    @ApiOperation(value = "删除群成员", notes = "删除群成员")
    @PostMapping("/remove")
    public RemoveGroupMemberRequest removeGroupMember(@RequestBody @ApiParam(name = "删除群成员参数", value = "传入json格式", required = true) RemoveGroupMemberRequest removeGroupMemberRequest) {
        return removeGroupMemberRequest;
    }

    @ApiOperation(value = "群成员退群", notes = "群成员退群")
    @PostMapping("/quit")
    public QuitGroupRequest quitGroup(@RequestBody @ApiParam(name = "群成员退群参数", value = "传入json格式", required = true) QuitGroupRequest quitGroupRequest) {
        return quitGroupRequest;
    }
}
