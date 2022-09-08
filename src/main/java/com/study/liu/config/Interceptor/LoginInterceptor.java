package com.study.liu.config.Interceptor;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.study.liu.utils.CommonResult;
import com.study.liu.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @description：登录验证，建议用redis实现，jwt存在过期token不在生效问题
 * @author     ：liu
 * @date       ：2022-08-31
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);


    @Value("${LoginPremission}")
    boolean LoginPremission;

    //处理器方法执行前调用
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //是否开启登录验证
        if (LoginPremission) {
            //判断是否登录状态
            String token = request.getHeader("token");
            token = token == null ? "" : token;   //判断token防止程序报错
            try {
                DecodedJWT tokenInfo = JwtUtil.getTokenInfo(token);
                String username = tokenInfo.getClaims().get("username").asString();
                return true;
            } catch (Exception e) {
                logger.debug("登录失败,拦截器处抛出");
                response.setContentType("json/text;charset=utf-8");
                PrintWriter out = response.getWriter();
                out.write(JSONObject.toJSONString(new CommonResult(400, "登录失败,拦截器处抛出", e)));
                return false;
            }
        } else {
            return true;
        }
    }

    //处理器方法执行后调用
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    //请求处理完毕后，响应视图前
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

