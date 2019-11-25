package com.opay.im.controller;

import com.opay.im.model.ChatGroupModel;
import com.opay.im.model.request.BlockGroupMemberRequest;
import com.opay.im.model.request.CreateGroupRequest;
import com.opay.im.model.request.GroupMemberQuitRequest;
import com.opay.im.model.request.InviteGroupRequest;
import com.opay.im.model.request.JoinGroupRequest;
import com.opay.im.model.request.MuteGroupRequest;
import com.opay.im.model.request.RemoveGroupMemberRequest;
import com.opay.im.model.request.UpdateGroupRequest;
import com.opay.im.model.response.ResultResponse;
import com.opay.im.model.response.SuccessResponse;
import com.opay.im.service.ChatGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/api/group")
@Api(value = "聊天群组功能API")
public class GroupController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ChatGroupService chatGroupService;

    @ApiOperation(value = "获取群组信息", notes = "获取群组信息")
    @GetMapping("{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "群组ID", required = true, paramType = "path", dataType = "Long")
    })
    public ChatGroupModel getGroupInfo(@PathVariable long id) {
        return chatGroupService.selectByPrimaryKey(id);
    }

    @ApiOperation(value = "获取我加入的群组列表", notes = "获取我加入的群组列表")
    @GetMapping
    public List<ChatGroupModel> getGroupList() throws Exception {
        return chatGroupService.selectGroupList(String.valueOf(request.getAttribute("opayId")));
    }

    @ApiOperation(value = "建群", notes = "建群")
    @PostMapping("/create")
    public ResultResponse createGroup(@RequestBody @ApiParam(name = "建群参数", value = "传入json格式", required = true) CreateGroupRequest createGroupRequest) throws Exception {
        createGroupRequest.setOpayId(String.valueOf(request.getAttribute("opayId")));
        chatGroupService.insert(createGroupRequest);
        return new SuccessResponse();
    }

    @ApiOperation(value = "修改群信息", notes = "修改群信息")
    @PostMapping("/update")
    public ResultResponse updateGroup(@RequestBody @ApiParam(name = "修改群信息参数", value = "传入json格式", required = true) UpdateGroupRequest updateGroupRequest) throws Exception {
        ChatGroupModel record = new ChatGroupModel();
        record.setId(updateGroupRequest.getId());
        record.setOpayId(String.valueOf(request.getAttribute("opayId")));
        record.setName(updateGroupRequest.getName());
        record.setImg(updateGroupRequest.getImg());
        record.setNotice(updateGroupRequest.getNotice());
        chatGroupService.updateByPrimaryKeySelective(record);
        return new SuccessResponse();
    }

    @ApiOperation(value = "邀请入群", notes = "邀请入群")
    @PostMapping("/invite")
    public InviteGroupRequest inviteGroup(@RequestBody @ApiParam(name = "邀请入群参数", value = "传入json格式", required = true) InviteGroupRequest inviteGroupRequest) {
        return inviteGroupRequest;
    }

    @ApiOperation(value = "加入群", notes = "加入群")
    @PostMapping("/join")
    public ResultResponse joinGroup(@RequestBody @ApiParam(name = "加入群参数", value = "传入json格式", required = true) JoinGroupRequest joinGroupRequest) throws Exception {
        joinGroupRequest.setOpayId(String.valueOf(request.getAttribute("opayId")));
        chatGroupService.joinGroup(joinGroupRequest);
        return new SuccessResponse();
    }

    @ApiOperation(value = "删除群成员", notes = "删除群成员")
    @PostMapping("/remove")
    public ResultResponse removeGroupMember(@RequestBody @ApiParam(name = "删除群成员参数", value = "传入json格式", required = true) RemoveGroupMemberRequest removeGroupMemberRequest) throws Exception {
        removeGroupMemberRequest.setOwnerOpayId(String.valueOf(request.getAttribute("opayId")));
        chatGroupService.removeGroupMember(removeGroupMemberRequest);
        return new SuccessResponse();
    }

    @ApiOperation(value = "群成员退群", notes = "群成员退群")
    @PostMapping("/quit")
    public ResultResponse quitGroup(@RequestBody @ApiParam(name = "群成员退群参数", value = "传入json格式", required = true) GroupMemberQuitRequest groupMemberQuitRequest) throws Exception {
        groupMemberQuitRequest.setOpayId(String.valueOf(request.getAttribute("opayId")));
        chatGroupService.leaveGroup(groupMemberQuitRequest);
        return new SuccessResponse();
    }

    @ApiOperation(value = "群免打扰", notes = "是否开启群免打扰")
    @PostMapping("/mute")
    public ResultResponse muteGroup(@RequestBody @ApiParam(name = "群免打扰参数", value = "传入json格式", required = true) MuteGroupRequest muteGroupRequest) throws Exception {
        muteGroupRequest.setOpayId(String.valueOf(request.getAttribute("opayId")));
        chatGroupService.muteGroup(muteGroupRequest);
        return new SuccessResponse();
    }

    @ApiOperation(value = "禁言", notes = "是否禁言群成员")
    @PostMapping("/block")
    public ResultResponse blockGroupMember(@RequestBody @ApiParam(name = "禁言群成员参数", value = "传入json格式", required = true) BlockGroupMemberRequest blockGroupMemberRequest) throws Exception {
        blockGroupMemberRequest.setOwnerOpayId(String.valueOf(request.getAttribute("opayId")));
        chatGroupService.blockGroupMember(blockGroupMemberRequest);
        return new SuccessResponse();
    }
}
