package com.boot.integration.controller;

import com.boot.integration.conf.repeat.CacheLock;
import com.boot.integration.conf.repeat.CacheParam;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/repeat")
public class RepeatController
{

    @CacheLock(prefix = "books")
    @RequestMapping(value = "/submit", method = RequestMethod.GET)
    public String query(@CacheParam(name = "token") @RequestParam String token)
    {
        return "success - " + token;
    }
}
