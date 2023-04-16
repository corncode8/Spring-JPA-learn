package com.cos.blog.handler;

import com.cos.blog.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice       // 어디에서든 exception이 발생하면 이 class로 들어오게 해줌
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)   // IllegalArgumentException만 실행 그냥 Exception하면 모든 Exception이 실행
    public ResponseDto<String> handleArgumentException(Exception e) {
        return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}
