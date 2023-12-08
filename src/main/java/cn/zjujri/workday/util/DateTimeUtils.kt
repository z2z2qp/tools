package cn.zjujri.workday.util

import java.time.LocalDateTime

fun LocalDateTime?.toFmtString(pattern:String = "yyyy-MM-dd HH:mm:ss"):String{
    if(this == null){
        return ""
    }
    return this.format(Cache.getDateTimeFormatter(pattern))
}
