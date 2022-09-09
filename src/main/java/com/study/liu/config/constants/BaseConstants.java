package com.study.liu.config.constants;



public interface BaseConstants {

    /**
     * 接口当中的常量,可以省略public static final；
     * 通过接口直接访问常量
     */
    String MESSAGE_SUCCESS  = "成功";
    String MESSAGE_FAIL  = "失败";
    Integer CODE_SUCCESS  = 200;   //请求成功
    String CODE_FAIL  = "400";  //请求失败
    Integer CODE_LOGIN=401;  //用户未登录

    String LoginUser="LoginToken" ;//所以token前面加上这个用于判断redis数据库里面哪些是登录的key
}
