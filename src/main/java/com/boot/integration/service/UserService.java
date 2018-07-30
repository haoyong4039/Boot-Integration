package com.boot.integration.service;

import com.boot.integration.constant.ResponseDto;
import com.boot.integration.model.User;

/**
 * Created by haoyong on 2018/1/4.
 */
public interface UserService {

    ResponseDto<User> queryUserRoles();
}
