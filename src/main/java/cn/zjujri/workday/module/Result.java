package cn.zjujri.workday.module;

public record Result<T>(int code,String message,T data) {
    
    public static <T> Result<T> ok() {
        return new Result<T>(200, null, null);
    }
    public static <T> Result<T> ok(T data) {
        return new Result<T>(200, null, data);
    }
    public static <T> Result<T> fail(String message) {
        return new Result<T>(200, message, null);
    }
}
