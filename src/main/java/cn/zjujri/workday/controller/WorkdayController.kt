package cn.zjujri.workday.controller

import cn.zjujri.workday.module.Result
import cn.zjujri.workday.module.Version
import cn.zjujri.workday.module.Workday
import cn.zjujri.workday.service.WorkdayService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.time.LocalDate
import java.time.Month
import java.time.format.DateTimeFormatter
import java.util.*

@Tag(name = "工作日期")
@RestController
class WorkdayController(val service: WorkdayService) {


    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")


    var buildTime: String = Version.BUILD_TIME

    @Operation(summary = "项目构建时间")
    @GetMapping("/buildTime")
    fun buildTime(): Result<String> {
        return Result.ok(buildTime)
    }

    @Operation(summary = "一段时间内的日期是否为工作日")
    @GetMapping("/isWorkdays")
    fun isWorkdays(
        @RequestParam(required = true) @Parameter(
            required = true,
            name = "start",
            description = "查询的日期格式为yyyy-MM-dd"
        ) start: String?,
        @RequestParam(required = true) @Parameter(
            required = true,
            name = "end",
            description = "查询的日期格式为yyyy-MM-dd"
        ) end: String?
    ): Mono<Result<List<Workday>>> {
        // 判断时间段是否为空
        return if (start === null || end === null) {
            Mono.just(Result.fail("时间段不能为空"))
        } else {
            // 返回工作日天数的结果
            service.isWorkdays(LocalDate.parse(start, formatter), LocalDate.parse(end, formatter))
                .map { Result.ok(it) }
        }
    }


    @Operation(summary = "是否为工作日")
    @GetMapping("/isWorkday")
    fun isWorkday(
        @RequestParam(required = false) @Parameter(
            required = false,
            name = "date",
            description = "查询的日期格式为yyyy-MM-dd"
        ) date: String?
    ): Mono<Result<Workday>> {

        val localDate = if (!Objects.isNull(date)) {
            // 如果传入了日期参数，则将当前日期设置为传入的日期
            LocalDate.parse(date!!, formatter)
        } else {
            // 获取当前日期
            LocalDate.now()
        }
        // 获取最小年份
        val min = service.minYear()
        // 获取最大年份
        val max = service.maxYear()
        // 如果传入的日期在最小年份之前，则返回失败结果
        if (localDate.isBefore(LocalDate.of(min, Month.JANUARY, 1))) {
            return Mono.just(Result.fail("日期不能早与 $min 年"))
        }
        // 如果传入的日期在最大年份之后，则返回失败结果
        if (localDate.isAfter(LocalDate.of(max, Month.DECEMBER, 31))) {
            return Mono.just(Result.fail("日期不能晚于 $max 年"))
        }
        // 调用服务类的方法判断传入的日期是否为工作日，并返回成功结果
        return service.isWorkday(localDate).map { Result.ok(it) }
    }


}
