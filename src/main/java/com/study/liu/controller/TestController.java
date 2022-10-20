package com.study.liu.controller;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import com.study.liu.Exception.CommonException;
import com.study.liu.mapper.db1.LoginDao;
import com.study.liu.service.TestService;
import com.study.liu.utils.CommonResult;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {

    @Autowired
    TestService testService;

/*    @Autowired
    @Qualifier("db1SqlSessionTemplate")
    SqlSessionTemplate db1sqlSessionTemplate;*/
    @Resource(name = "db1SqlSessionTemplate")
        SqlSessionTemplate db1sqlSessionTemplate;

//    @Resource(name = "Db1JdbcTemplate")
//    JdbcTemplate jdbcTemplate1;
//
//    @Resource(name = "Db2JdbcTemplate")
//    JdbcTemplate jdbcTemplate2;
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

    @RequestMapping("/test01")
    public void test01(){
        //SqlSession sqlSession = db1sqlSessionTemplate.getSqlSessionFactory().openSession();
        //LoginDao mapper = sqlSession.getMapper(LoginDao.class);
        //List<Map<String, Object>> list = mapper.selectMenuTreeAll();
        //System.out.println(list);
//
//        List<Map<String, Object>> list = jdbcTemplate1.queryForList("select * from sys_user");
//        System.out.println(list);
    }
    /**
     *
     * @description : 测试分布式事务回滚
     * @author      : liu
     * @date        : 2022-10-20
     */
    @RequestMapping("/test02")
    public void test02(){
        testService.test02();
    }
}
