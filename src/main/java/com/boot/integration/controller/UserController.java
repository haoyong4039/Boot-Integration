package com.boot.integration.controller;

import com.boot.integration.exeption.CustomCode;
import com.boot.integration.exeption.CustomException;
import com.boot.integration.service.UserService;
import com.boot.integration.util.web.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

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
    public Map<String, Object> queryUserRole(@PathVariable Long userId)
    {
        String retCode = CustomCode.SUCCESS.getValue();
        String retMsg = CustomCode.SUCCESS.getMessage();
        Object retData = null;

        try
        {
            logger.info("userId:{}", userId);

            retData = userService.queryUserRoles(userId);
        }
        catch (CustomException e)
        {
            logger.info("CustomException:{}",e);
            retCode = e.getValue();
            retMsg = e.getMessage();
        }

        return BaseResponse.getResponseMap(retCode, retMsg, retData);
    }

    @RequestMapping(value = "/query/principal", method = RequestMethod.GET)
    public void getPrincipal(Principal principal)
    {
        logger.info("principal:{}", principal);
    }
}
