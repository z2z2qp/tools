package cn.zjujri.workday.handler;

import java.time.DateTimeException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cn.zjujri.workday.module.Result;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DateTimeException.class)
    public Result<?> dateTimeException(DateTimeException e){
        return Result.fail("无效的日期:"+e.getMessage());
    }
    
}
