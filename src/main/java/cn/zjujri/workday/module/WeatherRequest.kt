package cn.zjujri.workday.module

data class WeatherRequest(
    var longitude: Double?,
    var latitude: Double?,
    var startDate: String?,
    var endDate: String?,
    var hourly: String?
)
