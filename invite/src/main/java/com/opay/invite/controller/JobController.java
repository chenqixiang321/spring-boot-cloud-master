package com.opay.invite.controller;


import com.opay.invite.job.JobManager;
import com.opay.invite.job.OpayJob;
import com.opay.invite.resp.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/opay/job")
public class JobController {

    @Autowired
    private JobManager jobManager;

    /**
     * 触发任务
     *
     * @param group
     * @param name
     * @param day
     * @return
     * @throws Exception
     */
    @PostMapping("/trigger")
    @ApiOperation(value = "临时定时任务", notes = "name:fix-user-trade （同步交易数据）name:fix-user-reward (返利); 目标天day:2019-12-12")
    public Result trigger(@RequestParam(value = "group", required = false, defaultValue = OpayJob.DEFAULT_GROUP) String group,
                                   @RequestParam("name") String name,
                                   @RequestParam("day") String day) throws Exception {
        jobManager.trigger(group, name, day);

        return Result.success();
    }
}
