package cn.zjujri.workday.module;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Result", title = "统一返回对象")
public record Result<T>(
        @Schema(title = "状态码", description = "200为正常请求响应，其他均为异常响应") int code,
        @Schema(title = "异常信息", description = "当code 不为200时有信息，为200时忽略") String message,
        @Schema(title = "响应数据", description = "当code 不为200时忽略") T data) {

    public static <T> Result<T> ok() {
        return new Result<T>(200, null, null);
    }

    public static <T> Result<CurrentWeather> ok(T data) {
        return new Result<T>(200, null, data);
    }

    public static <T> Result<T> fail(String message) {
        return new Result<T>(200, message, null);
    }
}
