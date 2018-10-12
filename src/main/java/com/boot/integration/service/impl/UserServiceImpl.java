package com.boot.integration.service.impl;

import com.boot.integration.conf.redis.RedisUtil;
import com.boot.integration.exeption.CustomException;
import com.boot.integration.exeption.CustomCode;
import com.boot.integration.mapper.UserMapper;
import com.boot.integration.model.User;
import com.boot.integration.service.UserService;
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
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    public User queryUserRoles(Long userId)
        throws CustomException
    {
        User user = new User();

        try
        {
            String userRoleKey = "USER_ROLE_" + userId;

            user = (User)redisUtil.get(userRoleKey);

            if (user == null)
            {
                user = userMapper.queryUserRole(userId);

                logger.info("user:{}", user);

                if (user == null)
                {
                    throw new CustomException(CustomCode.ERROR_USER_NOT_EXIST);
                }

                redisUtil.set(userRoleKey, user, 60);
            }
        }
        catch (CustomException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new CustomException(CustomCode.ERROR_SYSTEM.getValue(), e.getMessage());
        }

        return user;
    }
}
