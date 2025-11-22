package cn.zjujri.workday.repository

import cn.zjujri.workday.module.Workday
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

/**
 * 工作日存储库接口
 */
interface WorkdayRepository : JpaRepository<Workday, LocalDate> {

    /**
     * 根据日期范围查找工作日
     *
     * @param start 开始日期（包含）
     * @param end 结束日期（不包含）
     * @return 工作日列表
     */
    fun findByDateGreaterThanEqualAndDateLessThanEqual(start: LocalDate, end: LocalDate): List<Workday>

    /**
     * 获取工作日表中的最大日期
     *
     * @return 最大日期字符串
     */
    @Query(value = "select max(date) from workday ", nativeQuery = true)
    fun maxDate(): LocalDate

    /**
     * 获取工作日表中的最小日期
     *
     * @return 最小日期字符串
     */
    @Query(value = "select min(date) from workday ", nativeQuery = true)
    fun minDate(): LocalDate
}
