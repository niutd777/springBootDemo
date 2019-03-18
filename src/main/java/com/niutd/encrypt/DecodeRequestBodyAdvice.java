package com.niutd.encrypt;

import com.niutd.utils.AesEncryptUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * 请求数据解密
 *
 * @author: niutd
 * @date: 2019/3/15 14:24
 */
@RestControllerAdvice
public class DecodeRequestBodyAdvice implements RequestBodyAdvice {

    private final static Logger logger = LoggerFactory.getLogger(DecodeRequestBodyAdvice.class);

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.getMethodAnnotation(RequestDecode.class) != null
                && methodParameter.getParameterAnnotation(RequestBody.class) != null;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage request, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage request, MethodParameter parameter, Type targetType,
                                           Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        RequestDecode requestDecode = parameter.getMethodAnnotation(RequestDecode.class);
        if (requestDecode == null) {
            return request;//controller方法不要求加解密
        }
        String encryptType = request.getHeaders().getFirst("encryptType");//加密类型
        if (StringUtils.isEmpty(encryptType)) {
            return request;
        }
        SecurityMethod encodeMethodEnum = SecurityMethod.getByCode(encryptType);
        //这里灵活的可以支持到多种加解密方式
        switch (encodeMethodEnum) {
            case NULL: {//请求中没有配置加密类型 不处理
                break;
            }
            case AES: {
                request = new DecodedHttpInputMessage(request);
                break;
            }
            default: {
                break;//请求中没有配置加密类型 不处理
            }
        }
        return request;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    static class DecodedHttpInputMessage implements HttpInputMessage {
        HttpHeaders headers;
        InputStream body;

        public DecodedHttpInputMessage(HttpHeaders headers, InputStream body) {
            this.headers = headers;
            this.body = body;
        }

        public DecodedHttpInputMessage(HttpInputMessage inputMessage) {
            this.headers = inputMessage.getHeaders();
            try {
                this.body = IOUtils.toInputStream(AesEncryptUtils.decrypt(easpString(IOUtils.toString(inputMessage.getBody(), "UTF-8"))), "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public String easpString(String requestData) {
            if (requestData != null && !requestData.equals("")) {
                String s = "{\"requestData\":";
                if (!requestData.startsWith(s)) {
                    throw new RuntimeException("参数【requestData】缺失异常！");
                } else {
                    int closeLen = requestData.length() - 1;
                    int openLen = "{\"requestData\":".length();
                    String substring = StringUtils.substring(requestData, openLen, closeLen);
                    return substring;
                }
            }
            return "";
        }

        @Override
        public InputStream getBody() {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }
    }
}
