package cn.zjujri.workday.service

import cn.zjujri.workday.module.City
import cn.zjujri.workday.repository.CityRepository
import org.springframework.stereotype.Service

/**
 *
 *
 * @author 庄佳彬
 * @since 2024/12/3 20:30
 */

@Service
class CityService(private val cityRepository: CityRepository) {
    fun findByName(name: String): City? {
        return cityRepository.findByAdm2NameZHContainingOrAdm1NameZHContainingOrLocationNameZHContaining(
            name,
            name,
            name
        ).firstOrNull()
    }
}