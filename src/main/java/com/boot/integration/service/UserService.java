package com.boot.integration.service;

import com.boot.integration.constant.ResponseDto;
import com.boot.integration.exeption.CustomException;
import com.boot.integration.model.User;

import java.util.List;

/**
 * Created by haoyong on 2018/1/4.
 */
public interface UserService
{

    List<User> queryUserRoles(Long userId) throws CustomException;
}
