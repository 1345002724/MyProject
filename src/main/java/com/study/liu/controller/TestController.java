package com.study.liu.controller;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import com.study.liu.Exception.CommonException;
import com.study.liu.service.TestService;
import com.study.liu.utils.CommonResult;
import com.study.liu.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {

    @Autowired
    TestService testService;

    /**
     * @description：分页查询
     * @author ：liu
     * @date ：2021-08-31
     */
    @RequestMapping("/test")
    public CommonResult queryByPage() {

        CommonResult result = CommonResult.success();
        testService.queryByPage(result);
        return result;
    }

    public static void main(String[] args) {
        Map paramters = new HashMap();
        paramters.put("benzParam","博客");
        paramters.put("benzParam2","博客3333");
        Class<?> benzParam = paramters.get("benzParam").getClass();
        System.out.println(benzParam);

    }

}
