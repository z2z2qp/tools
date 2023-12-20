package cn.zjujri.workday.handler

import cn.zjujri.workday.module.Result
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.DateTimeException

/**
 * 全局异常处理类
 */
@RestControllerAdvice
class GlobalExceptionHandler {

    /**
     * 处理日期时间异常
     *
     * @param e 日期时间异常
     * @return 失败的结果
     */
    @ExceptionHandler(DateTimeException::class)
    fun dateTimeException(e: DateTimeException): Result<*> {
        return Result.fail<Any>("无效的日期:" + e.message)
    }

    /**
     * 处理异常
     *
     * @param e 异常
     * @return 失败的结果
     */
    @ExceptionHandler(Exception::class)
    fun exception(e: Exception): Result<*> {
        e.printStackTrace()
        return Result.fail<Any>("内部错误:" + e.message)
    }

}
