package cn.zjujri.workday.controller

import cn.zjujri.workday.module.CurrentWeather
import cn.zjujri.workday.module.Result
import cn.zjujri.workday.service.WeatherService
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.constraints.NotNull
import org.springframework.util.ObjectUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono


@Tag(name = "天气接口")
@RestController
@RequestMapping("/weather")
class WeatherController(private val weatherService: WeatherService) {

    companion object {
        const val DEFAULT_TIMEZONE = "Asia/Shanghai"
    }


    // https://open-meteo.com/en/docs
    @Operation(
        summary = "历史天气", parameters = [
            Parameter(name = "longitude", description = "经度", required = true),
            Parameter(name = "latitude", description = "纬度", required = true),
            Parameter(name = "startDate", description = "开始时间", required = true),
            Parameter(name = "endDate", description = "结束时间", required = true),
            Parameter(name = "hourly", description = "每小时数据使用\",\"隔开", required = true),
        ]
    )
    @GetMapping("history")
    fun weatherHistory(
        @NotNull @RequestParam(name = "longitude") longitude: Double?,
        @NotNull @RequestParam(name = "latitude") latitude: Double?,
        @NotNull @RequestParam(name = "startDate") startDate: String?,
        @NotNull @RequestParam(name = "endDate") endDate: String?,
        @NotNull @RequestParam(name = "hourly") hourly: String?
    ): Mono<String> {
        val list = ArrayList<String>()
        if (ObjectUtils.isEmpty(hourly)) {
            list.add("temperature_2m")
        } else {
            for (s in hourly!!.split(",")) {
                if (!ObjectUtils.isEmpty(s)) {
                    list.add(s)
                }
            }
        }
        return weatherService.forecastHistory(latitude, longitude, startDate, endDate, DEFAULT_TIMEZONE, list)
    }

    /**
     * @param longitude 经度
     * @param latitude 维度
     * @return
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    @Operation(
        summary = "当前天气", parameters = [
            Parameter(name = "longitude", description = "经度", required = true),
            Parameter(name = "latitude", description = "纬度", required = true)
        ]
    )
    @GetMapping("current")
    fun weatherCurrent(
        @NotNull @RequestParam(name = "longitude") longitude: Double,
        @NotNull @RequestParam(name = "latitude") latitude: Double
    ): Mono<Result<CurrentWeather>> {
        return weatherService.forecastCurrent(latitude, longitude, DEFAULT_TIMEZONE, true).map {
            val value = ObjectMapper().readerForMapOf(Object::class.java).readValue(it) as Map<String, Any>
            val currentWeather = value["current_weather"] as Map<*, *>
            val temperature = currentWeather["temperature"] as Number
            val windSpeed = currentWeather["windspeed"] as Number
            val windDirection = currentWeather["winddirection"] as Number
            val weatherCode = currentWeather["weathercode"] as Int
            val time = currentWeather["time"] as String
            Result.ok(CurrentWeather(temperature, windSpeed, windDirection, weatherCode, getWeather(weatherCode), time))
        }
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

    private fun getWeather(code: Int): String {
        return when (code) {
            0 -> "晴天"
            1, 2, 3 -> "大部多云"
            45, 48 -> "雾"
            51, 53, 55 -> "毛毛雨"
            56, 57 -> "冻毛毛雨"
            61, 63 -> "雨"
            66, 67 -> "冻雨"
            71, 73, 75 -> "阵雪"
            77 -> "雪粒"
            80, 81, 82 -> "雨夹雪"
            85, 86 -> "大雪"
            96 -> "雷暴"
            else -> "未知"
        }
    }

}
