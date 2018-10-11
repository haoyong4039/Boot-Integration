package com.boot.integration.conf.redis.lock;

import com.boot.integration.exeption.CustomException;

public interface SeckillService
{

    /**
     * 查询商品秒杀详情
     *
     * @param productId
     */
    String queryDetail(String productId);

    /**
     * 秒杀的逻辑方法
     *
     * @param productId
     */
    void secKill(String productId) throws CustomException;

}
