package com.stuty.liu.service;

import com.stuty.liu.mapper.db1.LoginDao;
import com.stuty.liu.utils.CommonResult;
import com.stuty.liu.utils.JwtUtil;
import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class LoginService {
    @Autowired
    LoginDao loginDao;

    public CommonResult login(Map<String, Object> LoginMap, CommonResult Result) {

        Map<String, Object> ResultLogin = new HashMap<>();
        //访客账号,不查询后台数据库,
        if (LoginMap.get("username").toString().equals("guest")) {
            String token = JwtUtil.getToken(LoginMap);
            ResultLogin.putAll(LoginMap);
            ResultLogin.put("token", token);
            Result.setData(ResultLogin);
        } else {
            ResultLogin = loginDao.login(LoginMap);
            if (ResultLogin.size() == 1) {
                String token = JwtUtil.getToken(LoginMap);
                ResultLogin.put("token", token);
                Result.setData(ResultLogin);
            } else {
                Result.setCode(400);
                Result.setMessage("失败");
                Result.setData("登录失败");
            }
        }
        return Result;
    }
}
