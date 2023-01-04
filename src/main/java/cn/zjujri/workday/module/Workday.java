package cn.zjujri.workday.module;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Schema(name = "是否为工作日")
@Entity
public class Workday {
    @Id
    @Schema(title = "日期")
    private LocalDate date;


    @Schema(title = "是否为工作日",description = "1是，0否")
    private int workday;

    @Schema(title = "描述",description = "正常工作日或周末为空，调休日期会显示节日")
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
