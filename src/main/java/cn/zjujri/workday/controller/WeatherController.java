package cn.zjujri.workday.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public String weatherCurrent(
            @NotNull @RequestParam(name = "longitude") Double longitude,
            @NotNull @RequestParam(name = "latitude") Double latitude) {
        var result = weatherService.forecastCurrent(latitude, longitude, DEFAULT_TIMEZONE, true);
        return result;
    }

}
