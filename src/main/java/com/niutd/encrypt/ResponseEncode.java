package com.niutd.encrypt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 加密响应数据  默认不加密
 *
 * @author: niutd
 * @date: 2019/3/15 15:19
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseEncode {

    SecurityMethod method() default SecurityMethod.NULL;

}
