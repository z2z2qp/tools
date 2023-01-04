package cn.zjujri.workday.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface WeatherService {

    // https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&current_weather=true&hourly=temperature_2m,relativehumidity_2m,windspeed_10m
    @GetExchange("/v1/forecast")
    String forecastHistory(
            @RequestParam(name = "latitude") Double latitude,
            @RequestParam(name = "longitude") Double longitude,
            @RequestParam(name = "start_date") String startDate,
            @RequestParam(name = "end_date") String endDate,
            @RequestParam(name = "timezone",defaultValue = "Asia/Shanghai") String timezone,
            @RequestParam(name = "hourly") List<String> hourly);

    @GetExchange("/v1/forecast")
    String forecastCurrent(
            @RequestParam(name = "latitude") Double latitude,
            @RequestParam(name = "longitude") Double longitude,
            @RequestParam(name = "timezone",defaultValue = "Asia/Shanghai") String timezone,
            @RequestParam(name = "current_weather",defaultValue = "true") Boolean currentWeather);
}
