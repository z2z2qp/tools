package cn.zjujri.workday.module

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "GPS", title = "GPS信息")
data class GPS(
    @param:Schema(title = "经度")
    var longitude: Double?,
    @param:Schema(title = "维度")
    var latitude: Double?,
    @param:Schema(title = "海拔")
    var seaLevel: Double?
) {
    constructor() : this(null, null, null)
}
