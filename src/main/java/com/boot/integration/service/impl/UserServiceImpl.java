package com.boot.integration.service.impl;

import com.boot.integration.conf.redis.RedisUtil;
import com.boot.integration.exeption.CustomException;
import com.boot.integration.exeption.CustomCode;
import com.boot.integration.mapper.UserMapper;
import com.boot.integration.model.User;
import com.boot.integration.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by haoyong on 2018/1/4.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService
{

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    public List<User> queryUserRoles(Long userId)
        throws CustomException
    {

        String key = "UserRoles-"+userId;

        boolean iKey = redisUtil.hasKey(key);

        List<User> userList = new ArrayList<>();

        try
        {
            if (iKey)
            {
                userList = (List<User>)redisUtil.getValue(key);

                log.info("[queryUserRoles from] - [redis] - data:{}", userList);
            }
            else
            {
                userList = userMapper.queryUserRole(userId);

                log.info("[queryUserRoles from] - [mysql] - data:{}", userList);

                if (userList.size() == 0){
                    throw new CustomException(CustomCode.ERROR_USER_NOT_EXIST.getValue());
                }
                redisUtil.setValue(key, userList, 60, TimeUnit.SECONDS);
            }
        }
        catch (Exception e)
        {
            throw e;
        }

        return userList;
    }
}
