package com.opay.invite.service;

import com.opay.invite.mapper.InviteMapper;
import com.opay.invite.mapper.OpayActiveCashbackMapper;
import com.opay.invite.mapper.RewardJobMapper;
import com.opay.invite.model.OpayActiveCashback;
import com.opay.invite.model.OpayMasterPupilAward;
import com.opay.invite.model.OpayUserOrder;
import com.opay.invite.stateconfig.AgentRoyaltyReward;
import com.opay.invite.stateconfig.RewardConfig;
import com.opay.invite.utils.DateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RewardJobService {

    @Autowired
    private RewardJobMapper rewardJobMapper;

    @Autowired
    private RewardConfig rewardConfig;

    @Autowired
    private InviteMapper inviteMapper;

    @Autowired
    private OpayActiveCashbackMapper cashbackMapper;

    public List<OpayUserOrder> getOpayUserOrderList(Integer day, int start, int pageSize) {
        return rewardJobMapper.getOpayUserOrderList(day,start,pageSize);
    }

    public List<OpayMasterPupilAward> calMasterPupilAward(List<OpayUserOrder> list) {
        List<AgentRoyaltyReward> royList = rewardConfig.getRoyList();
        Map<Integer, AgentRoyaltyReward> map = royList.stream().collect(Collectors.toMap(AgentRoyaltyReward::getAction, Function.identity()));
        List<OpayMasterPupilAward>  mpaList = new ArrayList<>();
        Date date = new Date();
//        int month = Integer.valueOf(DateFormatter.formatShortYMDate(new Date()));
//        int day= Integer.valueOf(DateFormatter.formatShortYMDDate(new Date()));
        for(int i=0;i<list.size();i++){
            OpayUserOrder opayUserOrder =  list.get(i);
            AgentRoyaltyReward agentRoyaltyReward = map.get(opayUserOrder.getType());
            BigDecimal masterReward = opayUserOrder.getActualAmount().multiply(agentRoyaltyReward.getMasterReward()).divide(BigDecimal.valueOf(100l)).setScale(0, RoundingMode.DOWN);
            BigDecimal pupilReward = opayUserOrder.getActualAmount().multiply(agentRoyaltyReward.getPupilReward()).divide(BigDecimal.valueOf(100l)).setScale(0, RoundingMode.DOWN);
            OpayMasterPupilAward master = new OpayMasterPupilAward(opayUserOrder.getMasterOpayId(),masterReward,
                    date,1, opayUserOrder.getType(), opayUserOrder.getActualAmount(),opayUserOrder.getMarkType(),null,
                    BigDecimal.ZERO,1,opayUserOrder.getMonth(),opayUserOrder.getDay());//师傅奖励
            OpayMasterPupilAward pupil = new OpayMasterPupilAward(opayUserOrder.getOpayId(),pupilReward,
                    date,1,opayUserOrder.getType(), opayUserOrder.getActualAmount(),opayUserOrder.getMarkType(),null,
                    masterReward,0,opayUserOrder.getMonth(),opayUserOrder.getDay());//徒弟奖励
            master.setPupilId(opayUserOrder.getOpayId());
            master.setOrderId(opayUserOrder.getOrderId());
            pupil.setOrderId(opayUserOrder.getOrderId());
            pupil.setPupilId(opayUserOrder.getOpayId());
            mpaList.add(master);
            mpaList.add(pupil);
        }
        return mpaList;
    }

    public List<OpayActiveCashback> getCashbackList(List<OpayMasterPupilAward> mplist) {
        Map<String, List<OpayMasterPupilAward>> collect = mplist.stream().collect(Collectors.groupingBy(OpayMasterPupilAward::getOpayId));
        List<OpayActiveCashback> list = new ArrayList<>();
        collect.entrySet().forEach(entry -> {
            List<OpayMasterPupilAward> palist =entry.getValue();
            BigDecimal totalReward = palist.stream().map(OpayMasterPupilAward::getReward).reduce(BigDecimal.ZERO, BigDecimal::add);
            OpayActiveCashback cashback = new OpayActiveCashback();
            cashback.setAmount(totalReward);
            cashback.setTotalAmount(totalReward);
            cashback.setOpayId(entry.getKey());
            cashback.setUpdateAt(new Date());
            list.add(cashback);
        });
        return list;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveAwardAndCashAndOrderStatus(List<OpayActiveCashback> nlist, List<OpayMasterPupilAward> mplist, List<OpayUserOrder> list) {
        rewardJobMapper.updateOpayUserOrder(list);
        inviteMapper.saveInviteReward(mplist);
        cashbackMapper.updateBatchCashback(nlist);
    }
}
