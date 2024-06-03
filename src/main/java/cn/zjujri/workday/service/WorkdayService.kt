package cn.zjujri.workday.service

import cn.zjujri.workday.module.Workday
import cn.zjujri.workday.repository.WorkdayRepository
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.stream.Collectors
import kotlin.jvm.optionals.getOrElse

@Service
class WorkdayService(private val workdayRepository: WorkdayRepository) {


    fun isWorkday(localDate: LocalDate): Workday {
        val result = workdayRepository.findById(localDate).getOrElse {
            val isWorkday = when (localDate.dayOfWeek) {
                DayOfWeek.SATURDAY, DayOfWeek.SUNDAY -> 0
                else -> 1
            }
            Workday(localDate, isWorkday)
        }
        return result
    }

    fun minYear(): Int {
        val year = workdayRepository.minDate().substring(0, 4)
        return year.toInt()
    }

    fun maxYear(): Int {
        val year = workdayRepository.maxDate().substring(0, 4)
        return year.toInt()

    }

    fun isWorkdays(start: LocalDate, end: LocalDate): List<Workday> {
        //查询时间段内所有假日办发布信息，并修改为Map<LocalDate, Workday?>
        val dbResult = workdayRepository.findByDateGreaterThanEqualAndDateLessThanEqual(start, end)
            .stream()
            .collect(Collectors.toMap({ it.date }, { it }, { k1, _ -> k1 }))
//                { HashMap() },
//                { r: HashMap<LocalDate, Workday>, t: Workday ->
//                    r[t.date!!] = t
//                },
//                { t, u -> t.putAll(u) })
        val result = ArrayList<Workday>()
        var day = start
        while (!day.isAfter(end)) {//循环从起始日期开始到结束日期
            val isWorkday = dbResult[day]?.workday
                ?: if (day.dayOfWeek == DayOfWeek.SUNDAY || day.dayOfWeek == DayOfWeek.SATURDAY) 0 else 1
            result.add(Workday(day, isWorkday))
            day = day.plusDays(1)
        }
        return result
    }

}
