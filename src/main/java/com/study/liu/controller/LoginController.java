package com.study.liu.controller;

import com.study.liu.service.LoginService;
import com.study.liu.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @RequestMapping("/login")
    public CommonResult login(@RequestBody Map<String,Object> request)  {

        CommonResult Result = CommonResult.success();
        return loginService.login(request,Result);
    }
}
