package cn.zjujri.workday.module;

import java.time.LocalDate;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Workday {
    @Id
    private LocalDate date;


    private int workday;

    private String reason;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getWorkday() {
        return workday;
    }

    public void setWorkday(int workday) {
        this.workday = workday;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    

}
