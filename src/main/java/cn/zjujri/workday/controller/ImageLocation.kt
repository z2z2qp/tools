package cn.zjujri.workday.controller

import cn.zjujri.workday.module.GPS
import cn.zjujri.workday.module.Result
import cn.zjujri.workday.service.MetaDataService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@Tag(name = "图片")
@RestController
@RequestMapping("image")
class ImageLocation(private val metaDataService: MetaDataService) {


    @Operation(summary = "获取经纬度")
    @PostMapping("/getLocation")
    fun getLocation(file: MultipartFile): Result<GPS> {
        val optional = metaDataService.parseLocation(file.inputStream)
        val gps = optional.map {
            GPS(it.longitude, it.latitude, null)
        }.orElse(GPS())
        return Result.ok(gps)
    }


}
