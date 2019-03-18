package com.niutd.encrypt;

import com.alibaba.fastjson.JSON;
import com.niutd.utils.AesEncryptUtils;
import com.niutd.utils.Result;
import com.niutd.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


/**
 * 返回数据加密 -data加密
 *
 * @author: niutd
 * @date: 2019/3/15 15:20
 */
@RestControllerAdvice
public class EncodeResponseBodyAdvice implements ResponseBodyAdvice {

    private final static Logger logger = LoggerFactory.getLogger(EncodeResponseBodyAdvice.class);

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return returnType.getMethodAnnotation(ResponseEncode.class) != null;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        ResponseEncode responseEncode = returnType.getMethodAnnotation(ResponseEncode.class);
        if (responseEncode == null) {
            return body;
        }
        String encryptType = request.getHeaders().getFirst("encryptType");
        SecurityMethod encodeMethodEnum = SecurityMethod.getByCode(encryptType);
        if (encodeMethodEnum == null) {
            return body;
        }
        int errorCode = ((Result) body).getErrorCode();
        String errorMsg = ((Result) body).getErrorMsg();
        Object data = ((Result) body).getData();
        Object encodedData = null;
        switch (encodeMethodEnum) {
            case NULL: {//请求中没有配置加密类型 不处理
                encodedData = data;
                break;
            }
            case AES: {
                encodedData = AesEncryptUtils.encrypt(JSON.toJSONString(data));
                break;
            }
            default: {
                encodedData = data;
                break;//请求中没有配置加密类型 不处理
            }
        }
        return ResultUtil.result(errorCode, errorMsg, encodedData);

    }
}
