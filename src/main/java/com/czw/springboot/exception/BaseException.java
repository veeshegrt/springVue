package com.czw.springboot.exception;



public class BaseException extends RuntimeException{

    private String code;

    public BaseException(String code,String msg){
        super(msg);
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}
