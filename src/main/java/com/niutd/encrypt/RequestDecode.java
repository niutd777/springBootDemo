package com.niutd.encrypt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 解密请求数据 默认不进行解密
 *
 * @author: niutd
 * @date: 2019/3/15 14:28
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestDecode {

    SecurityMethod method() default SecurityMethod.NULL;

}
