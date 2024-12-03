package cn.zjujri.workday.repository

import cn.zjujri.workday.module.City
import org.springframework.data.jpa.repository.JpaRepository

/**
 *
 *
 * @author 庄佳彬
 * @since 2024/12/3 20:14
 */
interface CityRepository : JpaRepository<City, Int>{

    fun findByAdm2NameZHContainingOrAdm1NameZHContainingOrLocationNameZHContaining(name1: String,name2: String,name3: String): List<City>

}