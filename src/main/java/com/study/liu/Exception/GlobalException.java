package com.study.liu.Exception;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description：全局异常处理
 * @author     ：liu
 * @date       ：2022-08-31
 */
@Slf4j
@ResponseBody
@ControllerAdvice
public class GlobalException {

    /**
     * 处理Controller抛出的异常
     *
     * @param e 异常实例
     * @return Controller层的返回值
     */
    @ExceptionHandler
    @ResponseBody
    public Object expHandler(Exception e)   throws Exception {
        log.error(e.toString());
        JSONObject json = new JSONObject();
        json.put("data", e);
        json.put("code", 500);
        json.put("msg", "系统错误");
        if (e instanceof CommonException) {
            json.put("data", e.getMessage());
        }
        else if (e.getMessage().contains("ORA-00001")) {
            json.put("data", "写入失败，有重复数据");
        }else {
            throw e;
        }
        return json.toString();
    }
}
