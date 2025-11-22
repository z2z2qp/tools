package cn.zjujri.workday.module

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

/**
 *
 *
 * @author 庄佳彬
 * @since 2024/12/3 20:15
 */
@Entity
@Schema(name = "City", title = "城市")
data class City(
    @Id
    @Column(name = "Location_id")
    var locationId: Int? = null,

    @Column(name = "Location_Name_EN")
    @param:Schema(title = "英文地理名")
    var locationNameEN: String? = null,

    @Column(name = "Location_Name_ZH")
    @param:Schema(title = "中文地理名")
    var locationNameZH: String? = null,

    @Column(name = "ISO_3166_1")
    @param:Schema(title = "国家代码")
    var iso31661: String? = null,

    @Column(name = "Country_Region_EN")
    @param:Schema(title = "英文国家名")
    var countryRegionEN: String? = null,

    @Column(name = "Country_Region_ZH")
    @param:Schema(title = "中文国家名")
    var countryRegionZH: String? = null,

    @Column(name = "Adm1_Name_EN")
    @param:Schema(title = "英文省名")
    var adm1NameEN: String? = null,

    @Column(name = "Adm1_Name_ZH")
    @param:Schema(title = "中文省名")
    var adm1NameZH: String? = null,

    @Column(name = "Adm2_Name_EN")
    @param:Schema(title = "英文市名")
    var adm2NameEN: String? = null,

    @Column(name = "Adm2_Name_ZH")
    @param:Schema(title = "中文市名")
    var adm2NameZH: String? = null,

    @Column(name = "Timezone")
    @param:Schema(title = "时区")
    var timezone: String? = null,

    @Column(name = "Latitude")
    @param:Schema(title = "纬度")
    var latitude: Double? = null,

    @Column(name = "Longitude")
    @param:Schema(title = "经度")
    var longitude: Double? = null,

    @Column(name = "AD_code")
    @param:Schema(title = "行政区划代码")
    var adCode: Int? = null
)
