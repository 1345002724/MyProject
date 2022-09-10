package com.study.liu.mapper.db2;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface TestDao {
    List<Map<String,Object>> queryByPage(Map<String, Object> Page);
}
