package cn.zjujri.workday.module

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Result", title = "统一返回对象")
data class Result<T>(
    @Schema(title = "状态码", description = "200为正常请求响应，其他均为异常响应") val code: Int,
    @Schema(title = "异常信息", description = "当code 不为200时有信息，为200时忽略") val message: String?,
    @Schema(title = "响应数据", description = "当code 不为200时忽略") val data: T?
) {

    companion object {
        fun <T> ok(): Result<T> {
            return Result(200, null, null)
        }

        fun <T> ok(data: T): Result<T> {
            return Result(200, null, data)
        }

        fun <T> fail(message: String): Result<T> {
            return Result(200, message, null)
        }
    }
}
