package com.opay.invite.utils;

import com.opay.invite.model.OpayActiveCashback;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;

public class CommonUtil {

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }


    /**
     * 计算cashback总额
     * @param cashbacklist
     * @return
     */
    public static BigDecimal calSumAmount(List<OpayActiveCashback> cashbacklist) {
        if (CollectionUtils.isEmpty(cashbacklist)) {
            return new BigDecimal(0);
        }
        return  cashbacklist.stream().map(x -> x.getAmount() == null ? new BigDecimal(0) : x.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }
}
