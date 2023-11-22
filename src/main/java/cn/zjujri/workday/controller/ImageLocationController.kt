package cn.zjujri.workday.controller

import cn.zjujri.workday.module.GPS
import cn.zjujri.workday.module.Result
import cn.zjujri.workday.service.MetaDataService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@Tag(name = "图片")
@RestController
@RequestMapping("image")
class ImageLocationController(private val metaDataService: MetaDataService) {


    @Operation(summary = "获取经纬度")
    @PostMapping("/getLocation")
    fun getLocation(@RequestPart("file") filePart: FilePart): Mono<Result<GPS>> {
        return filePart.content()
            .map {
                val gps = metaDataService.parseLocation(it.asInputStream())
                    .map { gl ->
                        GPS(gl.longitude, gl.latitude, null)
                    }.orElse(GPS())
                Result.ok(gps)
            }.next()
    }


}
