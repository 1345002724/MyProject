package com.study.liu.service;

import com.github.pagehelper.Page;
import com.study.liu.Exception.CommonException;
import com.study.liu.mapper.db1.TestDaoDb1_01;
import com.study.liu.mapper.db2.TestDaoDb2_02;
import com.study.liu.utils.CommonResult;
import com.study.liu.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TestService extends CommonUtil {
    @Resource
    TestDaoDb2_02 testDaoDb2_02;

    @Resource
    TestDaoDb1_01 testDaoDb1_01;

    public void queryByPage(CommonResult result) {

        try {
            //分页查询
            Map<String, Object> Page = new HashMap<>();
            Page.put("pageSize", "2");
            Page.put("pageNum", "1");
            Page<Object> page = paginationAll(Page);
            long total = page.getTotal(); //总数
            log.info(total + "");
            List<Map<String, Object>> list = testDaoDb2_02.queryByPage(Page);
            result.setData(list);

        } catch (Exception e) {
            result.setData(e.getMessage());
            result.setCode(400);
            result.setMessage("失败");
        }


    }

    @Transactional(rollbackFor = Exception.class,timeout = 60)
    public void test02() throws CommonException {


        Integer integer1 = testDaoDb1_01.test02_Db1();
        System.out.println(integer1.toString());
        Integer integer = testDaoDb2_02.test02();
        System.out.println(integer.toString());
        //throw  new CommonException("测试是否会回滚");
    }
}
