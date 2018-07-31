package com.boot.integration.service.impl;

import com.boot.integration.conf.redis.RedisUtil;
import com.boot.integration.constant.ResponseDto;
import com.boot.integration.constant.ResponseEnum;
import com.boot.integration.mapper.UserMapper;
import com.boot.integration.model.User;
import com.boot.integration.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by haoyong on 2018/1/4.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    public ResponseDto<User> queryUserRoles(){

        String key = "userRoles";

        if (redisUtil.hasKey(key)){
            log.info("[data from] - [redis]");
            return new ResponseDto<>(ResponseEnum.USER_ROLE_SUCCESS,null,(List<User>) redisUtil.getValue(key));
        }else {
            log.info("[data from] - [mysql]");
            List<User> userList = userMapper.queryUserRole();
            redisUtil.setValue(key,userList,60, TimeUnit.SECONDS);
            return new ResponseDto<>(ResponseEnum.USER_ROLE_SUCCESS,null,userList);
        }
    }
}
