package com.vn.minh.service.util.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.vn.minh.domain.response.ResponseData;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseData> postMethodName(Exception ex) {

        ResponseData res = new ResponseData();
        int statusCode = HttpStatus.BAD_GATEWAY.value();
        res.setStatus(statusCode);
        res.setError(("Một lỗi ngớ ngẩn nào đó chưa fix"));
        String message = ex.getMessage();
        res.setMessage(message);
        res.setData(null);
        return ResponseEntity.badRequest().body(res);
    }

    @ExceptionHandler(MessageCustomExcetion.class)
    public ResponseEntity<ResponseData> postMethodName(MessageCustomExcetion ex) {

        ResponseData res = new ResponseData();
        int statusCode = HttpStatus.BAD_GATEWAY.value();
        res.setStatus(statusCode);
        res.setError(("Ngoại lệ sai id"));
        String message = ex.getMessage();
        res.setMessage(message);
        res.setData(null);
        return ResponseEntity.badRequest().body(res);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ResponseData> notFoundException(NoResourceFoundException ex) {

        ResponseData res = new ResponseData();
        int statusCode = HttpStatus.BAD_GATEWAY.value();
        res.setStatus(statusCode);
        res.setError(("Không tìm thấy tài nguyên"));
        String message = ex.getMessage();
        res.setMessage(message);
        res.setData(null);
        return ResponseEntity.badRequest().body(res);
    }

}
