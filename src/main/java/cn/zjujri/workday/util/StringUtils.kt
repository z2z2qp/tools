package cn.zjujri.workday.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


fun String?.toLocalDateTime(pattern:String = "yyyy-MM-dd HH:mm:ss"): LocalDateTime? {
    if(this == null){
        return null
    }
    val fmt = Cache.getDateTimeFormatter(pattern)
    return LocalDateTime.parse(this,fmt)
}