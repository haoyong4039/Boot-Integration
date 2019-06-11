package com.boot.integration.conf.security;

import com.boot.integration.mapper.RoleMapper;
import com.boot.integration.mapper.UserMapper;
import com.boot.integration.model.Role;
import com.boot.integration.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by haoyong on 2018/1/4.
 */
@Component
public class CustomUserDetailsService implements UserDetailsService
{

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = new User();
        user.setUsername(username);
        user = userMapper.selectOne(user);

        if (user == null)
        {
            logger.info("Check Account Error : Not Exist");
            throw new UsernameNotFoundException("Account Not Exist");
        }

        Collection<SimpleGrantedAuthority> collection = new HashSet<SimpleGrantedAuthority>();
        Iterator<Role> iterator = roleMapper.queryRoleByUid(user.getId()).iterator();
        while (iterator.hasNext())
        {
            collection.add(new SimpleGrantedAuthority(iterator.next().getName()));
        }

        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), collection);
    }
}
