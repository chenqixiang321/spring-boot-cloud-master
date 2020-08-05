package com.opay.invite.service;

import com.opay.invite.mapper.RewardJobMapper;
import com.opay.invite.model.OpayInviteRelation;
import com.opay.invite.model.OpayUserOrder;
import com.opay.invite.model.OpayUserTask;
import com.opay.invite.model.request.OpayApiUserRecordRequest;
import com.opay.invite.model.response.OpayApiResultResponse;
import com.opay.invite.transferconfig.TransferConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserTradeService {
    @Autowired
    private RewardJobMapper rewardJobMapper;

    @Autowired
    private OpayApiService opayApiService;

    @Autowired
    private TransferConfig transferConfig;

    public List<OpayInviteRelation> getTaskRelationList(int start, int pageSize,int day,int type) {
        return rewardJobMapper.getTaskRelationList(start,pageSize,day,type);
    }

    public OpayApiResultResponse getUserTradeList(
            List<OpayInviteRelation> list, int subStart, int subPageSize,
            String endTime,String startTime,String serviceType) throws Exception {
        long mills = System.currentTimeMillis();
        OpayApiUserRecordRequest request = new OpayApiUserRecordRequest();
        request.setPageNo(String.valueOf(subStart));
        request.setPageSize(String.valueOf(subPageSize));
        request.setServiceType(serviceType);
        request.setUserId(requestUserIdsHandler(list));
        request.setStartTime(startTime);
        request.setEndTime(endTime);
        return opayApiService.queryUserRecordByUserId(transferConfig.getMerchantId(),String.valueOf(mills),request,transferConfig.getAesKey(),transferConfig.getIv());
    }

    private String requestUserIdsHandler(List<OpayInviteRelation> list) {
        StringBuffer sb = new StringBuffer();
        for(OpayInviteRelation re: list){
            sb.append(re.getPupilId()).append(",");
        }
        String userIds =sb.toString();
        return userIds.substring(0,userIds.length()-1);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveUserOrder(List<OpayUserOrder> userOrders, List<OpayInviteRelation> list,int day,int type) {
        List<OpayUserTask> userTasks = new ArrayList<>();
        for(OpayInviteRelation re:list){
            OpayUserTask userTask = new OpayUserTask();
            userTask.setDay(day);
            userTask.setType(type);
            userTask.setOpayId(re.getPupilId());
            userTasks.add(userTask);
        }
        rewardJobMapper.saveUserTask(userTasks);

        if(userOrders==null || userOrders.size()==0){
            return ;
        }
        List<OpayUserOrder> existlist = rewardJobMapper.selectUserOrderList(userOrders);
       if(existlist ==null || existlist.size()==0){
           rewardJobMapper.saveOpayUserOrder(userOrders);
           return ;
       }
        Map<String, OpayUserOrder> map = existlist.stream().collect(Collectors.toMap(OpayUserOrder::getOrderId, Function.identity()));
        List<OpayUserOrder> new_userOrders = new ArrayList<>();
        for(OpayUserOrder userOrder:userOrders){
            if(map.get(userOrder.getOrderId())==null){
                new_userOrders.add(userOrder);
            }
        }
        if(new_userOrders==null || new_userOrders.size()==0){
            return ;
        }
        //存入非存在的
        rewardJobMapper.saveOpayUserOrder(new_userOrders);
    }
}
