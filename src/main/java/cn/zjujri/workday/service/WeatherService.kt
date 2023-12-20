package cn.zjujri.workday.service

import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange
import reactor.core.publisher.Mono

/**
 * 天气服务接口
 */
@HttpExchange
interface WeatherService {

    // https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&current_weather=true&hourly=temperature_2m,relativehumidity_2m,windspeed_10m
    /**
     * 获取历史天气预报
     *
     * @param latitude 纬度
     * @param longitude 经度
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param timezone 时区
     * @param hourly 频率
     * @return 历史天气预报字符串
     */
    @GetExchange("/v1/forecast")
    fun forecastHistory(
        @RequestParam(name = "latitude") latitude: Double?,
        @RequestParam(name = "longitude") longitude: Double?,
        @RequestParam(name = "start_date") startDate: String?,
        @RequestParam(name = "end_date") endDate: String?,
        @RequestParam(name = "timezone", defaultValue = "Asia/Shanghai") timezone: String?,
        @RequestParam(name = "hourly") hourly: List<String>?
    ): Mono<String>

    /**
     * 获取当前天气
     *
     * @param latitude 纬度
     * @param longitude 经度
     * @param timezone 时区
     * @param currentWeather 是否显示当前天气信息
     * @return 当前天气字符串
     */
    @GetExchange("/v1/forecast")
    fun forecastCurrent(
        @RequestParam(name = "latitude") latitude: Double?,
        @RequestParam(name = "longitude") longitude: Double?,
        @RequestParam(name = "timezone", defaultValue = "Asia/Shanghai") timezone: String?,
        @RequestParam(name = "current_weather", defaultValue = "true") currentWeather: Boolean?
    ): Mono<String>
}
