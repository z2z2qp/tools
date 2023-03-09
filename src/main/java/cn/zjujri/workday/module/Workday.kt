package cn.zjujri.workday.module

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.Hibernate
import java.time.LocalDate

@Entity
@Schema(name = "Workday", title = "是否为工作日")
data class Workday(
    @Id
    @Schema(title = "日期")
    var date: LocalDate? = null,

    @Schema(title = "是否为工作日", description = "1是，0否")
    var workday: Int? = null,

    @Schema(title = "描述", description = "正常工作日或周末为空，调休日期会显示节日")
    var reason: String? = null
) {
    constructor() : this(null, null)
    constructor(date: LocalDate?, workday: Int?) : this(date, workday, null)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Workday

        return date != null && date == other.date
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(date = $date , workday = $workday , reason = $reason )"
    }
}
