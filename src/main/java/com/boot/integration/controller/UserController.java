package com.boot.integration.controller;

import com.boot.integration.constant.ResponseDto;
import com.boot.integration.model.User;
import com.boot.integration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by haoyong on 2018/1/4.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /*
      token获取
      http://localhost:6060/design/oauth/token?username=${_this.ruleForm2.account}&password=${_this.ruleForm2.checkPass}&grant_type=password&client_id=client&client_secret=secret
      mvn package -DskipTests（跳过测试打包）
     */

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/query/role",method = RequestMethod.GET)
    public ResponseDto<User> queryUserRole(){
        return userService.queryUserRoles();
    }

}
