package com.boot.integration.controller;

import com.boot.integration.conf.redis.RedisUtil;
import com.boot.integration.dto.ResponseDto;
import com.boot.integration.exeption.CustomCode;
import com.boot.integration.exeption.CustomException;
import com.boot.integration.model.User;
import com.boot.integration.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by haoyong on 2018/1/4.
 */
@RestController
@RequestMapping("/user")
public class UserController
{

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    /*
      token获取
      http://localhost:6060/design/oauth/token?username=${_this.ruleForm2.account}&password=${_this.ruleForm2.checkPass}&grant_type=password&client_id=client&client_secret=secret
      token刷新
      http://localhost:6060/design/oauth/token?grant_type=refresh_token&refresh_token=1d749fbf-88f1-4b1d-a222-8d7ebead337c&client_id=client&client_secret=secret
      mvn package -DskipTests（跳过测试打包）
     */

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/query/role/{userId}", method = RequestMethod.GET)
    public ResponseDto queryUserRole(@PathVariable Long userId)
    {
        ResponseDto responseDto = new ResponseDto();

        int retCode = CustomCode.SUCCESS.getValue();

        try
        {
            List<User> userList = userService.queryUserRoles(userId);

            responseDto.setRetCode(retCode);
            responseDto.setRetData(userList);
        }
        catch (CustomException e)
        {
            logger.error("Custom Error !!!", e);

            retCode = e.getValue();
            responseDto.setRetCode(retCode);
        }
        catch (Exception e)
        {
            logger.error("System Error !!!", e);

            retCode = CustomCode.ERROR_SYSTEM.getValue();
            responseDto.setRetCode(retCode);
            responseDto.setRetData(e.getMessage());
        }

        return responseDto;
    }
}
