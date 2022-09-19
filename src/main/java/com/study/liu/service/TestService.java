package com.study.liu.service;

import com.github.pagehelper.Page;
import com.study.liu.Exception.CommonException;
import com.study.liu.mapper.db2.TestDao;
import com.study.liu.utils.CommonResult;
import com.study.liu.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TestService extends CommonUtil {
    @Autowired
    TestDao testDao;

    public void queryByPage(CommonResult result) {

        try {
            //分页查询
            Map<String, Object> Page = new HashMap<>();
            Page.put("pageSize", "2");
            Page.put("pageNum", "1");
            Page<Object> page = paginationAll(Page);
            long total = page.getTotal(); //总数
            log.info(total + "");
            List<Map<String, Object>> list = testDao.queryByPage(Page);
            result.setData(list);

        } catch (Exception e) {
            result.setData(e.getMessage());
            result.setCode(400);
            result.setMessage("失败");
        }


    }
}
