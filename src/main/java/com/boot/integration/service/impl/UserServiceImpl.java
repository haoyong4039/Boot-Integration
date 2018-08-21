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

    public List<User> queryUserRoles() throws CustomException
    {

        String key = "userRoles";

        boolean iKey = redisUtil.hasKey(key);

        List<User> userList = new ArrayList<>();

        if (iKey)
        {
            userList = (List<User>)redisUtil.getValue(key);

            log.info("[queryUserRoles from] - [redis] - data:{}",userList);
        }
        else
        {
            try
            {
                userList = userMapper.queryUserRole();

                log.info("[queryUserRoles from] - [mysql] - data:{}",userList);
            }
            catch (Exception e)
            {
                e.printStackTrace();

                throw new CustomException(CustomCode.ERROR_DB_EXECUTE.getValue());
            }

            redisUtil.setValue(key, userList, 60, TimeUnit.SECONDS);
        }
        return userList;
    }
}
