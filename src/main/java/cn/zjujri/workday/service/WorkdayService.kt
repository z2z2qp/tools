package cn.zjujri.workday.service

import cn.zjujri.workday.module.Workday
import cn.zjujri.workday.repository.WorkdayRepository
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.concurrent.atomic.AtomicReference

@Service
open class WorkdayService(private val workdayRepository: WorkdayRepository) {


    fun isWorkday(localDate: LocalDate): Workday {
        val result = AtomicReference<Workday>()
        workdayRepository.findById(localDate).ifPresentOrElse({
            result.set(it)
        }, {
            val isWorkday = when (localDate.dayOfWeek) {
                DayOfWeek.SATURDAY, DayOfWeek.SUNDAY -> 0
                else -> 1
            }
            result.set(Workday(localDate, isWorkday))
        })
        return result.get()
    }

}
