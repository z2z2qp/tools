package cn.zjujri.workday.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.zjujri.workday.module.Workday;

public interface WorkdayRepository extends JpaRepository<Workday,LocalDate>{
    
}
