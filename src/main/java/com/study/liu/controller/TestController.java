package com.study.liu.controller;

import com.study.liu.service.TestService;
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

    @RequestMapping("/test")
    public void test(){
        testService.TestService();
    }

}
