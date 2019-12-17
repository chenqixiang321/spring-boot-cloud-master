package com.opay.invite.service;

import com.opay.invite.config.PrizePoolConfig;
import com.opay.invite.model.PrizeModel;
import com.opay.invite.model.response.LuckDrawInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Random;

@Component
public class AliasMethodService {
    private double[] firstPoolProbability;
    private int[] firstPoolAlias;
    private int firstPoolLength;
    private Random rand = new Random();
    private List<Double> firstPoolProb;

    private double[] secondPoolProbability;
    private int[] secondPoolAlias;
    private int secondPoolLength;
    private List<Double> secondPoolProb;
    @Autowired
    private PrizePoolConfig prizePoolConfig;
    @Autowired
    private RedisTemplate redisTemplate;
    @Value("${prize-pool.count}")
    private int prizePoolCount;

    public void init() {
        for (int i = 0; i < prizePoolConfig.getFirstPool().size(); i++) {
            PrizeModel prizeModel = prizePoolConfig.getFirstPool().get(i);
            redisTemplate.opsForValue().set("prize_pool:first" + ":" + i, prizeModel.getProb().multiply(new BigDecimal(String.valueOf(prizePoolCount))).intValue());
        }
        for (int i = 0; i < prizePoolConfig.getSecondPool().size(); i++) {
            PrizeModel prizeModel = prizePoolConfig.getSecondPool().get(i);
            redisTemplate.opsForValue().set("prize_pool:second" + ":" + i, prizeModel.getProb().multiply(new BigDecimal(String.valueOf(prizePoolCount))).intValue());
        }
        redisTemplate.opsForValue().set("prize_pool:count", prizePoolCount);
        initFirstPool();
        initSecondPool();
    }

    private void initFirstPool() {
        firstPoolProb = new ArrayList<>();
        for (PrizeModel prizeModel : prizePoolConfig.getFirstPool()) {
            firstPoolProb.add(prizeModel.getProb().doubleValue());
        }
        if (firstPoolProb == null || rand == null)
            throw new NullPointerException();
        if (firstPoolProb.size() == 0)
            throw new IllegalArgumentException("Probability vector must be nonempty.");


        this.rand = rand;
        this.firstPoolLength = firstPoolProb.size();
        this.firstPoolProbability = new double[firstPoolLength];
        this.firstPoolAlias = new int[firstPoolLength];


        double[] probtemp = new double[firstPoolLength];
        Deque<Integer> small = new ArrayDeque<>();
        Deque<Integer> large = new ArrayDeque<>();


        for (int i = 0; i < firstPoolLength; i++) {
            probtemp[i] = firstPoolProb.get(i) * firstPoolLength;
            if (probtemp[i] < 1.0)
                small.add(i);
            else
                large.add(i);
        }


        while (!small.isEmpty() && !large.isEmpty()) {
            int less = small.pop();
            int more = large.pop();
            firstPoolProbability[less] = probtemp[less];
            firstPoolAlias[less] = more;
            probtemp[more] = probtemp[more] - (1.0 - firstPoolProbability[less]);
            if (probtemp[more] < 1.0)
                small.add(more);
            else
                large.add(more);
        }

        while (!small.isEmpty())
            firstPoolProbability[small.pop()] = 1.0;
        while (!large.isEmpty())
            firstPoolProbability[large.pop()] = 1.0;
    }

    private void initSecondPool() {
        secondPoolProb = new ArrayList<>();
        for (PrizeModel prizeModel : prizePoolConfig.getFirstPool()) {
            secondPoolProb.add(prizeModel.getProb().doubleValue());
        }
        if (secondPoolProb == null || rand == null)
            throw new NullPointerException();
        if (secondPoolProb.size() == 0)
            throw new IllegalArgumentException("Probability vector must be nonempty.");


        this.rand = rand;
        this.secondPoolLength = secondPoolProb.size();
        this.secondPoolProbability = new double[secondPoolLength];
        this.secondPoolAlias = new int[secondPoolLength];


        double[] probtemp = new double[secondPoolLength];
        Deque<Integer> small = new ArrayDeque<>();
        Deque<Integer> large = new ArrayDeque<>();


        for (int i = 0; i < secondPoolLength; i++) {
            probtemp[i] = secondPoolProb.get(i) * secondPoolLength;
            if (probtemp[i] < 1.0)
                small.add(i);
            else
                large.add(i);
        }


        while (!small.isEmpty() && !large.isEmpty()) {
            int less = small.pop();
            int more = large.pop();
            secondPoolProbability[less] = probtemp[less];
            secondPoolAlias[less] = more;
            probtemp[more] = probtemp[more] - (1.0 - secondPoolProbability[less]);
            if (probtemp[more] < 1.0)
                small.add(more);
            else
                large.add(more);
        }

        while (!small.isEmpty())
            secondPoolProbability[small.pop()] = 1.0;
        while (!large.isEmpty())
            secondPoolProbability[large.pop()] = 1.0;
    }

    public int firstPoolAliasMethod() {
        int column = rand.nextInt(firstPoolLength);
        boolean coinToss = rand.nextDouble() < firstPoolProbability[column];
        return coinToss ? column : firstPoolAlias[column];
    }

    public int secondPoolAliasMethod() {
        int column = rand.nextInt(secondPoolLength);
        boolean coinToss = rand.nextDouble() < secondPoolProbability[column];
        return coinToss ? column : secondPoolAlias[column];
    }
}
