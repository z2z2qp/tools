package cn.zjujri.workday.service

import cn.zjujri.workday.module.Workday
import cn.zjujri.workday.repository.WorkdayRepository
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.concurrent.atomic.AtomicReference
import java.util.function.BiConsumer
import java.util.function.Supplier

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

    fun isWorkdays(start: LocalDate, end: LocalDate): List<Workday> {
        //查询时间段内所有假日办发布信息，并修改为Map<LocalDate, Workday?>
        val dbResult = workdayRepository.findByDateGreaterThanEqualAndDateLessThanEqual(start, end)
            .stream()
            .collect(
                { HashMap() },
                { r: HashMap<LocalDate, Workday>, t: Workday ->
                    r[t.date!!] = t
                },
                { t, u -> t.putAll(u) })
        val result = ArrayList<Workday>()
        var day = start
        while (day.isBefore(end)) {//循环从起始日期开始到结束日期
            val r = dbResult[day]
            if (r === null){//查询假日办信息，若无按双休计算工作日
                val weekend = day.dayOfWeek == DayOfWeek.SUNDAY || day.dayOfWeek == DayOfWeek.SATURDAY
                val isWorkday = if(weekend) 0 else 1
                val workday = Workday(day,isWorkday)
                result.add(workday)
            }else{//假日办信息直接插入
                result.add(r)
            }
            day = day.plusDays(1)
        }
        return result
    }

}