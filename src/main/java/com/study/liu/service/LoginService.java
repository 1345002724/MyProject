package com.study.liu.service;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.study.liu.Exception.CommonException;
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

    public void login(Map<String, Object> request, CommonResult Result) {

        Map<String, Object> LoginMap = new HashMap<>();
        try {
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
                    throw new CommonException("账号或密码不正确");
                }
            }
        } catch (CommonException e) {
            Result.setCode(400);
            Result.setMessage("失败");
            Result.setData(e.getMessage() + e);
        } catch (Exception e) {
            Result.setCode(400);
            Result.setMessage("失败");
            Result.setData("获取用户信息失败" + e);
        }
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

    public void getInfo(CommonResult result, String token) {


        Map<String, Object> data = new HashMap<>();
        Map<String, Object> MapUser = new HashMap<>();
        try {
            DecodedJWT tokenInfo = JwtUtil.getTokenInfo(token);
            String username = tokenInfo.getClaims().get("username").asString();
            String password = tokenInfo.getClaims().get("password").asString();
            Map<String, Object> map = new HashMap<>();
            map.put("username", username);
            map.put("password", password);
            List<Map<String, Object>> LoginList = loginDao.login(map);
            if (LoginList.size() == 1) {
                MapUser = LoginList.get(0);
            } else {
                throw new RuntimeException();
            }
            //roles设置
            Set<String> roles = new HashSet<>();
            roles.add("admin");
            //permissions设置
            Set<String> permissions = new HashSet<>();
            permissions.add("*:*:*");
            data.put("user", MapUser);
            data.put("roles", roles);
            data.put("permissions", permissions);
            result.setData(data);
        } catch (Exception e) {
            result.setCode(400);
            result.setMessage("失败");
            result.setData("获取用户信息失败");
        }

    }

}
