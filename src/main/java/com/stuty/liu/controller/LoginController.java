package com.stuty.liu.controller;

import com.alibaba.fastjson.JSON;
import com.stuty.liu.service.LoginService;
import com.stuty.liu.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    /**
     * @description：系统登录实现
     * @author     ：liu
     * @date       ：2022-08-31
     */
    @PostMapping("/login")
    public CommonResult login(String LoginBody)  {

        CommonResult Result = CommonResult.success();
        Map<String,Object> map = JSON.parseObject(LoginBody);

        return loginService.login(map,Result);
    }
}
