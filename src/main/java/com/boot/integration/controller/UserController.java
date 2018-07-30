package com.boot.integration.controller;

import com.boot.integration.conf.rabbit.MessageProducer;
import com.boot.integration.constant.ResponseDto;
import com.boot.integration.constant.ResponseEnum;
import com.boot.integration.mapper.UserMapper;
import com.boot.integration.model.User;
import com.boot.integration.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by haoyong on 2018/1/4.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageProducer messageProducer;

    /*
      token获取
      http://localhost:6060/design/oauth/token?username=${_this.ruleForm2.account}&password=${_this.ruleForm2.checkPass}&grant_type=password&client_id=client&client_secret=secret
     */

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/query/role",method = RequestMethod.GET)
    public ResponseDto<User> queryUserRole(){
        return userService.queryUserRoles();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/test/rabbit",method = RequestMethod.GET)
    public void testRabbit(){
        User user = new User();
        user.setUsername("author");
        messageProducer.sendMessage(user);
    }
}
