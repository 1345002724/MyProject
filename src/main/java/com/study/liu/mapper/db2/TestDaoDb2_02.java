package com.study.liu.mapper.db2;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface TestDaoDb2_02 {
    List<Map<String,Object>> queryByPage(Map<String, Object> Page);
    Integer test02();
}
