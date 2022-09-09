package com.study.liu.service;


import com.alibaba.fastjson.JSON;
import com.study.liu.mapper.db1.LoginDao;
import com.study.liu.utils.CommonResult;
import com.study.liu.utils.JwtUtil;
import com.study.liu.utils.login.LoginUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

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

    public List<Map<String, Object>> selectMenuTreeByUserId(Map<String, Object> request) {
        List<Map<String, Object>> menus = null;
        Long userId = (Long) request.get("userId");
        String username = request.get("username") + "";

        //访客用户从配置文件读取菜单
        if (username.equals("guest")) {
            log.info("访客用户从文件读取菜单列表");
        }
        menus = loginDao.selectMenuTreeAll();

        List<Map<String, Object>> childPerms = LoginUtils.getParentList(menus, 0);
        return childPerms;
    }

}
