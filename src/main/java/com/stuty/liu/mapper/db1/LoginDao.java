package com.stuty.liu.mapper.db1;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface LoginDao {
    Map<String,Object> login(Map<String,Object> map);
}
