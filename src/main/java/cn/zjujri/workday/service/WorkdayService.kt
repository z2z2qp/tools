package cn.zjujri.workday.service

import cn.zjujri.workday.module.Workday
import cn.zjujri.workday.repository.WorkdayRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.stream.Collectors
import kotlin.jvm.optionals.getOrElse

@Service
class WorkdayService(private val workdayRepository: WorkdayRepository) {


    fun isWorkday(localDate: LocalDate): Mono<Workday> {
        return Mono.justOrEmpty(localDate)
            .map { workdayRepository.findById(it) }
            .map {
                it.getOrElse {
                    val isWorkday = when (localDate.dayOfWeek) {
                        DayOfWeek.SATURDAY, DayOfWeek.SUNDAY -> 0
                        else -> 1
                    }
                    Workday(localDate, isWorkday)
                }
            }
//        val result = workdayRepository.findById(localDate).getOrElse {
//            val isWorkday = when (localDate.dayOfWeek) {
//                DayOfWeek.SATURDAY, DayOfWeek.SUNDAY -> 0
//                else -> 1
//            }
//            Workday(localDate, isWorkday)
//        }
//        return result
    }

    fun minYear(): Int {
        val year = workdayRepository.minDate().substring(0, 4)
        return year.toInt()
    }

    fun maxYear(): Int {
        val year = workdayRepository.maxDate().substring(0, 4)
        return year.toInt()

    }

    fun isWorkdays(start: LocalDate, end: LocalDate): Mono<List<Workday>> {
        //查询时间段内所有假日办发布信息，并修改为Map<LocalDate, Workday?>
        return Mono.zip(Mono.just(start), Mono.just(end))
            .map { tp2 ->
                val dbResult = workdayRepository.findByDateGreaterThanEqualAndDateLessThanEqual(tp2.t1, tp2.t2)
                    .stream()
                    .collect(Collectors.toMap({ it.date }, { it }, { k1, _ -> k1 }))
                val result = ArrayList<Workday>()
                var day = start
                while (!day.isAfter(end)) {//循环从起始日期开始到结束日期
                    val isWorkday = dbResult[day]?.workday
                        ?: if (day.dayOfWeek == DayOfWeek.SUNDAY || day.dayOfWeek == DayOfWeek.SATURDAY) 0 else 1
                    result.add(Workday(day, isWorkday))
                    day = day.plusDays(1)
                }
                result
            }


//        val dbResult = workdayRepository.findByDateGreaterThanEqualAndDateLessThanEqual(start, end)
//            .stream()
//            .collect(Collectors.toMap({ it.date }, { it }, { k1, _ -> k1 }))
////                { HashMap() },
////                { r: HashMap<LocalDate, Workday>, t: Workday ->
////                    r[t.date!!] = t
////                },
////                { t, u -> t.putAll(u) })
//        val result = ArrayList<Workday>()
//        var day = start
//        while (!day.isAfter(end)) {//循环从起始日期开始到结束日期
//            val isWorkday = dbResult[day]?.workday
//                ?: if (day.dayOfWeek == DayOfWeek.SUNDAY || day.dayOfWeek == DayOfWeek.SATURDAY) 0 else 1
//            result.add(Workday(day, isWorkday))
//            day = day.plusDays(1)
//        }
//        return result
    }

}
