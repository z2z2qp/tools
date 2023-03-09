package cn.zjujri.workday.module

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "WeatherRequest", title = "天气请求")
data class WeatherRequest(
    @Schema(title = "经度")
    var longitude: Double?,
    @Schema(title = "维度")
    var latitude: Double?,
    @Schema(title = "开始时间")
    var startDate: String?,
    @Schema(title = "结束时间")
    var endDate: String?,
    @Schema(title = "每小时数据使用\",\"隔开")
    var hourly: String?
)
