package cn.zjujri.workday.service

import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange

@HttpExchange
interface WeatherService {

    // https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&current_weather=true&hourly=temperature_2m,relativehumidity_2m,windspeed_10m
    @GetExchange("/v1/forecast")
    fun forecastHistory(
        @RequestParam(name = "latitude") latitude: Double?,
        @RequestParam(name = "longitude") longitude: Double?,
        @RequestParam(name = "start_date") startDate: String?,
        @RequestParam(name = "end_date") endDate: String?,
        @RequestParam(name = "timezone", defaultValue = "Asia/Shanghai") timezone: String?,
        @RequestParam(name = "hourly") hourly: List<String>?): String

    @GetExchange("/v1/forecast")
    fun forecastCurrent(
        @RequestParam(name = "latitude") latitude: Double?,
        @RequestParam(name = "longitude") longitude: Double?,
        @RequestParam(name = "timezone", defaultValue = "Asia/Shanghai") timezone: String?,
        @RequestParam(name = "current_weather", defaultValue = "true") currentWeather: Boolean?): String
}
