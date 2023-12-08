package cn.zjujri.workday.util

import java.time.format.DateTimeFormatter

class Cache {

    companion object INSTANCE  {
        private val DATE_TIME_FORMATTER = HashMap<String, DateTimeFormatter>()
        fun getDateTimeFormatter(pattern:String = "yyyy-MM-dd HH:mm:ss"):DateTimeFormatter{
            return DATE_TIME_FORMATTER.computeIfAbsent(pattern) { DateTimeFormatter.ofPattern(pattern) }
        }
    }
    
}