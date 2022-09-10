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
}
