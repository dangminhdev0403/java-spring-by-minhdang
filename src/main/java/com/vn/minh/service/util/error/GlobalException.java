package com.vn.minh.service.util.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.vn.minh.domain.response.ResponseData;

@RestControllerAdvice
@SuppressWarnings("rawtypes")

public class GlobalException {

    private ResponseData createResponseData(int statusCode, String error, String message) {

        ResponseData res = new ResponseData();
        res.setStatus(statusCode);
        res.setError(error);

        res.setMessage(message);
        res.setData(null);
        return res;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseData> ComonExcetion(Exception ex) {

        int statusCode = HttpStatus.BAD_REQUEST.value();
        String error = "Một lỗi ngớ ngẩn nào đó chưa fix";

        String message = ex.getMessage();

        ResponseData res = createResponseData(statusCode, error, message);

        return ResponseEntity.badRequest().body(res);
    }

    @ExceptionHandler(MessageCustomExcetion.class)

    public ResponseEntity<ResponseData> idException(MessageCustomExcetion ex) {

        int statusCode = HttpStatus.BAD_REQUEST.value();
        String error = "Ngoại lệ sai id";

        String message = ex.getMessage();

        ResponseData res = createResponseData(statusCode, error, message);

        return ResponseEntity.badRequest().body(res);
    }

    @ExceptionHandler(NoResourceFoundException.class)

    public ResponseEntity<ResponseData> notFoundException(NoResourceFoundException ex) {
        ResponseData res = new ResponseData();
        int statusCode = HttpStatus.NOT_FOUND.value();
        res.setStatus(statusCode);
        res.setError(("Không tìm thấy tài nguyên"));
        String message = ex.getMessage();
        res.setMessage(message);
        res.setData(null);
        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponseData> notFoundUserException(UsernameNotFoundException ex) {
        ResponseData res = new ResponseData();
        int statusCode = HttpStatus.NOT_FOUND.value();
        res.setStatus(statusCode);
        res.setError(("Thông tin người dùng không đúng"));
        String message = ex.getMessage();
        res.setMessage(message);
        res.setData(null);
        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }

}
