package cn.zjujri.workday.repository

import cn.zjujri.workday.module.Workday
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface WorkdayRepository : JpaRepository<Workday, LocalDate>{


    fun findByDateGreaterThanEqualAndDateLessThanEqual(start: LocalDate, end: LocalDate): List<Workday>
}
