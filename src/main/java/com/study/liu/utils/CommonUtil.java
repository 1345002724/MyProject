package com.study.liu.utils;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.study.liu.Exception.CommonException;
import java.util.List;
import java.util.Map;

public class CommonUtil   {

    /**
     * description：判断是否为空
     * author     ：liu
     * date       ：2021-08-31
     */
    public void isNUll(Map<String, Object> map, String s) throws CommonException {
        if (map.get(s) == null || map.get(s).toString().equals("")) {
            throw new CommonException(s + "不能为空");
        }
    }

    public void isNUll(Map<String, Object> map, List<String> list) throws CommonException {
        for (String s : list) {
            if (map.get(s) == null || map.get(s).toString().equals("")) {
                throw new CommonException(s + "不能为空");
            }
        }
    }

    /**
     * description：分页插件
     * author     ：liu
     * date       ：2021-08-31
     */
    public void pagination(Map<String, Object> req) throws CommonException {
        isNUll(req, "pageNum");
        isNUll(req, "pageSize");
        Integer pageNum = Integer.valueOf(req.get("pageNum").toString());
        Integer pageSize = Integer.valueOf(req.get("pageSize").toString());
        PageHelper.startPage(pageNum, pageSize);

    }

    /**
     * description：分页插件并返回
     * author     ：liu
     * date       ：2021-08-31
     */
    public Page<Object> paginationAll(Map<String, Object> req) throws CommonException {
        isNUll(req, "pageNum");
        isNUll(req, "pageSize");
        Integer pageNum = Integer.valueOf(req.get("pageNum").toString());
        Integer pageSize = Integer.valueOf(req.get("pageSize").toString());
        Page<Object> Page = PageHelper.startPage(pageNum, pageSize);
        return Page;

    }
}
