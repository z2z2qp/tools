package cn.zjujri.workday.handler


import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

import cn.zjujri.workday.module.Result
import java.time.DateTimeException

@RestControllerAdvice
open class GlobalExceptionHandler {

    @ExceptionHandler(DateTimeException::class)
    open fun dateTimeException(e: DateTimeException): Result<*> {
        return Result.fail<Any>("无效的日期:" + e.message)
    }

    @ExceptionHandler(Exception::class)
    open fun exception(e: Exception): Result<*> {
        e.printStackTrace()
        return Result.fail<Any>("内部错误:" + e.message)
    }

}
