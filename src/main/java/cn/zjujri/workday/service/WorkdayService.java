package cn.zjujri.workday.service;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.stereotype.Service;

import cn.zjujri.workday.module.Workday;
import cn.zjujri.workday.repository.WorkdayRepository;

@Service
public class WorkdayService {

    private final WorkdayRepository repository;

    public WorkdayService(WorkdayRepository repository) {
        this.repository = repository;
    }

    public Workday isWorkday(LocalDate localDate) {
        var result = new AtomicReference<Workday>();
        repository.findById(localDate).ifPresentOrElse(it -> {
            result.set(it);
        }, () -> {
            var dayOfWeek = localDate.getDayOfWeek();
            var isWorkday = switch (dayOfWeek) {
                case SATURDAY, SUNDAY -> 0;
                default -> 1;
            };
            var workday = new Workday();
            workday.setDate(localDate);
            workday.setWorkday(isWorkday);
            result.set(workday);
        });
        return result.get();
    }

}
