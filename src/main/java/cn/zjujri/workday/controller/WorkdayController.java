package cn.zjujri.workday.controller;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.zjujri.workday.module.Result;
import cn.zjujri.workday.module.Workday;
import cn.zjujri.workday.service.WorkdayService;

@RestController
public class WorkdayController {


    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final WorkdayService service;
    public WorkdayController(WorkdayService service) {
        this.service = service;
    }

    @Value("${app.time}")
    private String buildTime;

    @GetMapping("/buildTime")
    private Result<String> buildTime(){
        return Result.ok(buildTime);
    } 

    @GetMapping("/isWorkday")
    private Result<Workday> isWorkday(String date){
        var localDate = LocalDate.now();
        if(!Objects.isNull(date)){
            localDate = LocalDate.parse(date,formatter);
        }
        if(localDate.isBefore(LocalDate.of(2023, Month.JANUARY, 1))){
            return Result.fail("日期不能早与 2023年");
        }
        if(localDate.isAfter(LocalDate.of(2023, Month.DECEMBER, 31))){
            return Result.fail("日期不能晚于 2023年");
        }
        return Result.ok(service.isWorkday(localDate));
    } 
    
}
