package com.study.liu.service;

import com.study.liu.mapper.db1.LoginDao;
import com.study.liu.utils.CommonResult;
import com.study.liu.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class LoginService {
    @Autowired
    LoginDao loginDao;

    public CommonResult login(Map<String, Object> request, CommonResult Result) {

        Map<String, Object> LoginMap = new HashMap<>();
        //访客账号,不查询后台数据库,
        if (request.get("username").toString().equals("guest")) {
            String token = JwtUtil.getToken(request);
            LoginMap.putAll(request);
            LoginMap.put("token", token);
            Result.setData(LoginMap);
        } else {
            List<Map<String, Object>> LoginList = loginDao.login(request);
            if (LoginList.size() == 1) {
                LoginMap = LoginList.get(0);
                String token = JwtUtil.getToken(request);
                LoginMap.put("token", token);
                Result.setData(LoginMap);
            } else {
                Result.setCode(400);
                Result.setMessage("失败");
                Result.setData("登录失败,账号或密码不正确");
            }
        }
        return Result;
    }
}
