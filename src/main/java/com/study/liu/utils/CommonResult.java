package com.study.liu.utils;


/**
 * 返回
 * @param <T>
 */

public class CommonResult<T> {
    private Integer code;  //状态码
    private String  message; //状态码信息
    private T       data;   //具体数据

    public CommonResult(Integer code, String message, T data){
        this.code=code;
        this.message=message;
        this.data=data;
    }

    public CommonResult() {
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static CommonResult success()
    {
        return new CommonResult(200,"成功","");
    }
    public static CommonResult failed(Object data)
    {
        return new CommonResult(400,"失败",data);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
