package cn.zjujri.workday.module

data class GPS(
    var longitude: Double?,
    var latitude: Double?,
    var seaLeavel: Double?){
    constructor():this(null,null,null)
}
