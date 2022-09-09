package com.study.liu.mapper.db1;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface LoginDao {

    List<Map<String, Object>> selectMenuTreeAll() ;

    List<Map<String,Object>> login(Map<String,Object> map);
}
