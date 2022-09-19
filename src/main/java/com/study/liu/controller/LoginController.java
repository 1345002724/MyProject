package com.study.liu.controller;


import com.study.liu.service.LoginService;
import com.study.liu.utils.CommonResult;
import com.study.liu.utils.login.LoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    /**
     * @description：系统登录实现
     * @author     ：liu
     * @date       ：2021-08-31
     */
    @RequestMapping("/login")
    public CommonResult login(@RequestBody Map<String,Object> request)  {

        CommonResult Result = CommonResult.success();
        loginService.login(request,Result);
        return Result;
    }

    /**
     * @description：获取路由信息
     * @author     ：liu
     * @date       ：2021-08-31
     */
    @RequestMapping("getRouters")
    public CommonResult getRouters(Map<String,Object> request)
    {
        Long userId = (Long) request.get("userId");
        List<Map<String, Object>> menus = loginService.selectMenuTreeByUserId(request);

        CommonResult result = CommonResult.success();

        LoginUtils loginUtils =new LoginUtils();
        result.setData(loginUtils.buildMenus(menus));
        return result;
    }


    /**
     * @description：获取用户信息
     * @author     ：liu
     * @date       ：2021-08-31
     */
    @GetMapping("getInfo")
    public CommonResult getInfo(HttpServletRequest request)
    {
        CommonResult result=CommonResult.success();
        String token = request.getHeader("Authorization");
        loginService.getInfo(result,token);
        return result;
    }
    /**
     * @description：退出登录
     * @author     ：liu
     * @date       ：2021-08-31
     */
    @RequestMapping("/logout")
    public CommonResult logout()
    {
        CommonResult result=CommonResult.success();
        result.setData("退出登录成功");
        return result;
    }
}
