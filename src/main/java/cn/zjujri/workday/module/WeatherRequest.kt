package cn.zjujri.workday.module

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "WeatherRequest", title = "天气请求")
data class WeatherRequest(
    @param:Schema(title = "经度")
    var longitude: Double?,
    @param:Schema(title = "维度")
    var latitude: Double?,
    @param:Schema(title = "开始时间")
    var startDate: String?,
    @param:Schema(title = "结束时间")
    var endDate: String?,
    @param:Schema(title = "每小时数据使用\",\"隔开")
    var hourly: String?
)
