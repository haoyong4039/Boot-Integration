package com.boot.integration.controller;

import com.boot.integration.conf.repeat.CacheLock;
import com.boot.integration.conf.repeat.CacheParam;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/repeat")
public class RepeatController
{

    @CacheLock(prefix = "books")
    @CacheParam(name = "access_token")
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String submit(@RequestParam String access_token)
    {
        return "success - " + access_token;
    }
}
