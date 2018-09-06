package com.boot.integration.mapper;

import com.boot.integration.model.User;
import com.boot.integration.util.BaseMapper;

import java.util.List;

public interface UserMapper extends BaseMapper<User>
{
    User queryUserRole(Long userId);
}
