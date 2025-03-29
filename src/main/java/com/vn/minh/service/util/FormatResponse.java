package com.vn.minh.service.util;

import java.lang.reflect.Method;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.vn.minh.domain.model.ApiDescription;
import com.vn.minh.domain.response.ResponseData;

import jakarta.servlet.http.HttpServletResponse;

@RestControllerAdvice
@SuppressWarnings({ "null", "rawtypes" })
public class FormatResponse implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {

        return true;
    }

    @Override
    @Nullable
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType,
            Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
        int status = servletResponse.getStatus();
        ResponseData<Object> rs = new ResponseData<>();
        rs.setStatus(status);

        if (body instanceof String || status >= 400) {

            return body;

        } else {
            rs.setData(body);
            String message = getApiDescription(returnType);
            rs.setMessage(message != null ? message : "Call API Thành Công");

        }
        return rs;
    }

    private String getApiDescription(MethodParameter returnType) {
        try {
            Method method = returnType.getMethod();
            if (method != null) {
                ApiDescription apiDescription = method.getAnnotation(ApiDescription.class);
                if (apiDescription != null) {
                    return apiDescription.value();
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Xử lý lỗi nếu cần
        }
        return null; // Trả về null nếu không tìm thấy annotation
    }

}
