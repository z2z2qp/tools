package cn.zjujri.workday.module

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "CurrentWeather", title = "当前天气")
data class CurrentWeather(
    @Schema(title = "气温")
    var temperature: Number?,
    @Schema(title = "风速")
    var windSpeed: Number?,
    @Schema(title = "风向")
    var windDirection: Number?,
    @Schema(title = "天气码", description = "wmo code 标准码 https://open-meteo.com/en/doc 底部")
    var weatherCode: Number?,
    @Schema(title = "天气", description = "根据code转换后")
    var weather: String?,
    @Schema(title = "时间")
    var time: String?
)
