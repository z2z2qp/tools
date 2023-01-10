package cn.zjujri.workday.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import cn.zjujri.workday.module.CurrentWeather;
import cn.zjujri.workday.module.Result;
import cn.zjujri.workday.module.WeatherRequest;
import cn.zjujri.workday.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;

@Tag(name = "天气接口")
@Validated
@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    private static final String DEFAULT_TIMEZONE = "Asia/Shanghai";

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    // https://open-meteo.com/en/docs
    @Operation(summary = "历史天气", parameters = {
            @Parameter(name = "longitude", description = "经度", required = true),
            @Parameter(name = "latitude", description = "纬度", required = true),
            @Parameter(name = "startDate", description = "开始时间", required = true),
            @Parameter(name = "endDate", description = "结束时间", required = true),
            @Parameter(name = "hourly", description = "每小时数据使用\",\"隔开", required = true),
    })
    @GetMapping("history")
    public String weatherHistory(
            @NotNull @RequestParam(name = "longitude") Double longitude,
            @NotNull @RequestParam(name = "latitude") Double latitude,
            @NotNull @RequestParam(name = "startDate") String startDate,
            @NotNull @RequestParam(name = "endDate") String endDate,
            @NotNull @RequestParam(name = "hourly") String hourly) {
        var list = new ArrayList<String>();
        if (ObjectUtils.isEmpty(hourly)) {
            list.add("temperature_2m");
        } else {
            var strs = hourly.split(",");
            for (String s : strs) {
                if (!ObjectUtils.isEmpty(s)) {
                    list.add(s);
                }
            }
        }
        var result = weatherService.forecastHistory(latitude, longitude, startDate, endDate, DEFAULT_TIMEZONE, list);
        return result;
    }

    @Operation(summary = "当前天气", parameters = {
            @Parameter(name = "longitude", description = "经度", required = true),
            @Parameter(name = "latitude", description = "纬度", required = true)
    })
    @GetMapping("current")
    public Result<CurrentWeather> weatherCurrent(
            @NotNull @RequestParam(name = "longitude") Double longitude,
            @NotNull @RequestParam(name = "latitude") Double latitude)
            throws JsonMappingException, JsonProcessingException {
        var result = weatherService.forecastCurrent(latitude, longitude, DEFAULT_TIMEZONE, true);
        Map<String, Object> value = new ObjectMapper().readerForMapOf(Object.class).readValue(result);
        Map<String, Object> currentWeather = (Map<String, Object>) value.get("current_weather");
        Double temperature = (Double) currentWeather.get("temperature");
        Double windspeed = (Double) currentWeather.get("windspeed");
        Double winddirection = (Double) currentWeather.get("winddirection");
        Integer weathercode = (Integer) currentWeather.get("weathercode");
        String time = (String) currentWeather.get("time");
        var currentWeatherRecord = new CurrentWeather(temperature, windspeed, winddirection, weathercode, getWeather(weathercode), time);
        return Result.ok(currentWeatherRecord);
    }

    /**
     * Code Description
     * 0 Clear sky
     * 1, 2, 3 Mainly clear, partly cloudy, and overcast
     * 45, 48 Fog and depositing rime fog
     * 51, 53, 55 Drizzle: Light, moderate, and dense intensity
     * 56, 57 Freezing Drizzle: Light and dense intensity
     * 61, 63, 65 Rain: Slight, moderate and heavy intensity
     * 66, 67 Freezing Rain: Light and heavy intensity
     * 71, 73, 75 Snow fall: Slight, moderate, and heavy intensity
     * 77 Snow grains
     * 80, 81, 82 Rain showers: Slight, moderate, and violent
     * 85, 86 Snow showers slight and heavy
     * 95 * Thunderstorm: Slight or moderate
     * 96, 99 * Thunderstorm with slight and heavy hail
     * 
     * @param code
     * @return
     */

    private String getWeather(int code) {
        return switch(code){
            case 0 -> "晴天";
            case 1,2,3 -> "大部多云";
            case 45,48->"雾";
            case 51,53,55->"毛毛雨";
            case 56,57->"冻毛毛雨";
            case 61,63->"雨";
            case 66,67->"冻雨";
            case 71,73,75->"阵雪";
            case 77 ->"雪粒";
            case 80,81,82->"雨夹雪";
            case 85,86->"大雪";
            case 96->"雷暴";
            default -> "未知";
        };
    }

}
