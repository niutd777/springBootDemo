package com.niutd.encrypt;

/**
 * 加解密类型枚举类
 *
 * @author: niutd
 * @date: 2019/3/15 14:41
 */
public enum SecurityMethod {
    NULL("", "不进行加解密操作"),
    AES("aes", "AES方式加解密操作");

    public static SecurityMethod getByCode(String str) {
        SecurityMethod[] securityMethods = values();
        for (SecurityMethod securityMethod : securityMethods) {
            if (securityMethod.getCode().equals(str)) {
                return securityMethod;
            }
        }
        return null;
    }

    private String code;
    private String msg;

    private SecurityMethod(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }}
