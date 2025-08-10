package com.suai.library.common.resp;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {
    //响应数据
    private T data;
    //提示信息
    private String message;
    //0成功，1失败
    private Integer code;
    //返回操作成功响应结果（带响应数据）
    public static <E> Result<E> success(E data) {return new Result<>(data,"success",0);}
    //无响应数据
    public static Result success(){return new Result(null,"success",0);}
    //返回操作失败响应结果
    public static Result error(String message) {return new Result(null,message,1);}
}
