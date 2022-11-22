package com.example.community.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.util.Assert;
import java.io.Serializable;

/**
 * @author sunxw
 * @description 公共返回类
 * @since 1.0-Snapshoot
 */
public class CommonResult<T> implements Serializable {

    /** 成功与否 */
    private Boolean success;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 将传入的result对象，转换成另外一个泛型结果
     *
     * 因为A方法返回的CommonResult对象，不满足调用其的B方法的返回，所以需要继续转换。
     *
     * @param result 传入的result对象
     * @param <T> 返回的泛型
     * @return 新的CommonResult对象
     */
    public static <T> CommonResult<T> error(CommonResult<?> result) {
        return error(result.getCode(), result.getMessage());
    }

    public static <T> CommonResult<T> error(Integer code, String message) {
        Assert.isTrue(!ResponseStatusCode.SUCCESS.equals(code), "code必须是错误的");
        CommonResult<T> commonResult = new CommonResult<>();
        commonResult.success = false;
        commonResult.code = code;
        commonResult.message = message;
        return commonResult;
    }

    public static <T> CommonResult<T> success(String message,T data) {
        CommonResult<T> result = new CommonResult<>();
        result.success = true;
        result.code = ResponseStatusCode.SUCCESS;
        result.data = data;
        result.message = message;
        return result;
    }

    @JsonIgnore //忽略避免jackson序列化给前端
    public boolean isSuccess() {//方便判断是否成功
        return ResponseStatusCode.SUCCESS.equals(code);
    } //判断是否成功

    @JsonIgnore
    public boolean isError() {
        return !isSuccess();
    }

    @Override
    public String toString() {
        return "CommonResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
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