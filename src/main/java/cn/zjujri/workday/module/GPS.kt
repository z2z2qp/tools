package cn.zjujri.workday.module

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "GPS", title = "GPS信息")
data class GPS(
    @Schema(title = "经度")
    var longitude: Double?,
    @Schema(title = "维度")
    var latitude: Double?,
    @Schema(title = "海拔")
    var seaLevel: Double?){
    constructor():this(null,null,null)
}
