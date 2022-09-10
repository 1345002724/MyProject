package com.study.liu.Exception;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

/**

 /**
 * @description：通用接口异常处理
 * @author     ：liu
 * @date       ：2021-08-31
 */
@Slf4j
public class CommonException extends RuntimeException {

    public  String  message;
    public CommonException(String  message){
        this.message=message;
    }
    @Override
    public String getMessage() {
        return this.message;
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
        this.message=message;
    }

    public Object faildJson(Exception e){
        log.error(e.toString());
        JSONObject json = new JSONObject();
        json.put("data", e);
        json.put("code", 400);
        json.put("msg", "系统错误");
        return json.toJSONString(json);
    }
}
