package com.niutd.utils;

/**
 * 返回结果工具类
 *
 * @author: niutd
 * @date: 2019/3/14 17:38
 */
public class ResultUtil {

    /**
     * 请求成功
     *
     * @param data :
     * @return : com.quickbusiness.utils.Result<T>
     * @author : niutd
     * @date : 2019/3/14 17:48
     */
    public static <T> Result<T> success(T data) {
        return commonResult(1, "请求成功", data);
    }

    /**
     * 请求失败
     *
     * @param errorMsg :
     * @return : com.quickbusiness.utils.Result<T>
     * @author : niutd
     * @date : 2019/3/14 17:49
     */
    public static <T> Result<T> error(String errorMsg) {
        return error(0, errorMsg);
    }

    /**
     * 请求失败 -自定义
     *
     * @param errorCode :
     * @param errorMsg  :
     * @return : com.quickbusiness.utils.Result<T>
     * @author : niutd
     * @date : 2019/3/14 17:49
     */
    public static <T> Result<T> error(int errorCode, String errorMsg) {
        return commonResult(errorCode, errorMsg, null);
    }

    /**
     * 请求结果
     *
     * @param errorCode :
     * @param errorMsg  :
     * @param data      :
     * @return : com.quickbusiness.utils.Result<T>
     * @author : niutd
     * @date : 2019/3/15 17:05
     */
    public static <T> Result<T> result(int errorCode, String errorMsg, T data) {
        return commonResult(errorCode, errorMsg, data);
    }

    private static <T> Result<T> commonResult(int errorCode, String errorMsg, T data) {
        Result<T> result = new Result<>();
        result.setErrorCode(errorCode);
        result.setErrorMsg(errorMsg);
        result.setData(data);
        return result;
    }
}
