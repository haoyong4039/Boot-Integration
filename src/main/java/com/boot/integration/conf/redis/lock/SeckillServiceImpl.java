package com.boot.integration.conf.redis.lock;

import com.boot.integration.exeption.CustomCode;
import com.boot.integration.exeption.CustomException;
import com.boot.integration.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SeckillServiceImpl implements SeckillService
{

    @Autowired
    private RedisLock redisLock;

    private static final int TIMEOUT = 10 * 1000;//超时时间 10s

    /**
     * 活动，特价，限量10份
     */
    static Map<String, Integer> products;//模拟商品信息表

    static Map<String, Integer> stock;//模拟库存表

    static Map<String, String> orders;//模拟下单成功用户表

    static
    {
        products = new HashMap<>();
        stock = new HashMap<>();
        orders = new HashMap<>();
        products.put("10010", 10);
        stock.put("10010", 10);
    }

    private String queryMap(String productId)
    {
        //模拟查询数据库
        return "限量" + products.get(productId) + "份,还剩:" + stock.get(productId) + "份,成功下单人数:" + orders.size() + "人";
    }

    @Override
    public String queryDetail(String productId)
    {
        return this.queryMap(productId);
    }

    @Override
    public void secKill(String productId)
        throws CustomException
    {
        // 1.查询该商品库存，为0则活动结束
        int stockNum = stock.get(productId);
        if (stockNum == 0)
        {
            throw new CustomException(CustomCode.ERROR_TIME_OVER);
        }

        // 2.加锁
        long time = System.currentTimeMillis() + TIMEOUT;

        // 加锁成功
        if (redisLock.lock(productId, String.valueOf(time)))
        {
            // 3.下单
            orders.put(KeyUtil.getUniqueKey(), productId);

            try
            {
                Thread.sleep(100);//模拟减库存的处理时间
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            // 4.减库
            stock.put(productId, stockNum - 1);
        }
        else
        {
            throw new CustomException(CustomCode.ERROR_USER_MORE);
        }

        // 5.解锁
        redisLock.unlock(productId, String.valueOf(time));
    }
}
