package com.study.liu.service;

import com.study.liu.mapper.db2.TestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TestService {
    @Autowired
    TestDao testDao;
    public void TestService(){
        List<Map<String, Object>> list = testDao.TestDao();
        System.out.println(list.toString());
    }
}
