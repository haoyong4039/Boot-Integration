package com.boot.integration.mapper;

import com.boot.integration.model.Role;
import com.boot.integration.util.BaseMapper;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role>
{
    List<Role> queryRoleByUid(Long userId);
}
