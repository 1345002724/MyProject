package com.study.liu.mapper.db1;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface LoginDao {
    List<Map<String,Object>> login(Map<String,Object> map);
}
