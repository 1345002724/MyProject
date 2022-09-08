package com.study.liu.controller;

import com.study.liu.service.LoginService;
import com.study.liu.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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

    /**
     * @description：获取路由信息
     * @author     ：liu
     * @date       ：2022-08-31
     */
    @RequestMapping("getRouters")
    public CommonResult getRouters(Map<String,Object> request)
    {
        Long userId = (Long) request.get("userId");
        List<Map<String,Object>> menus = loginService.selectMenuTreeByUserId(request);
        CommonResult result = CommonResult.success();
        result.setData(menus);
        return result;
    }
}
