package cn.zjujri.workday.controller

import cn.zjujri.workday.module.Result
import cn.zjujri.workday.module.Workday
import cn.zjujri.workday.service.WorkdayService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.Month
import java.time.format.DateTimeFormatter
import java.util.*

@Tag(name = "工作日期")
@RestController
open class WorkdayController(val service: WorkdayService) {


    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")


    @Value("\${app.time}")
    var buildTime: String? = null

    @Operation(summary = "项目构建时间")
    @GetMapping("/buildTime")
    fun buildTime(): Result<String> {
        return Result.ok(buildTime!!)
    }

    @Operation(summary = "是否为工作日")
    @GetMapping("/isWorkday")
    fun isWorkday(
        @RequestParam(required = false) @Parameter(required = false, name = "date", description = "查询的日期格式为yyyy-MM-dd") date: String?
    ): Result<Workday> {
        var localDate = LocalDate.now()
        if (!Objects.isNull(date)) {
            localDate = LocalDate.parse(date, formatter)
        }
        if (localDate.isBefore(LocalDate.of(2023, Month.JANUARY, 1))) {
            return Result.fail("日期不能早与 2023年")
        }
        if (localDate.isAfter(LocalDate.of(2023, Month.DECEMBER, 31))) {
            return Result.fail("日期不能晚于 2023年")
        }
        return Result.ok(service.isWorkday(localDate))
    }

}
