package cn.zjujri.workday.repository

import cn.zjujri.workday.module.Workday
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface WorkdayRepository : JpaRepository<Workday, LocalDate>{


    fun findByDateGreaterThanEqualAndDateLessThanEqual(start: LocalDate, end: LocalDate): List<Workday>

    @Query(value = "select max(date) from workday ",nativeQuery=true)
    fun maxDate():String
    @Query(value = "select min(date) from workday ",nativeQuery=true)
    fun minDate():String
}
