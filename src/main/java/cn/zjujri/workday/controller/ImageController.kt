package cn.zjujri.workday.controller

import cn.zjujri.workday.module.GPS
import cn.zjujri.workday.module.Result
import cn.zjujri.workday.service.MetaDataService
import cn.zjujri.workday.util.toFmtString
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@Tag(name = "图片")
@RestController
@RequestMapping("image")
class ImageController(private val metaDataService: MetaDataService) {


    @Operation(summary = "获取图片经纬度")
    @PostMapping("/getLocation", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun getLocation(@RequestPart("file") filePart: FilePart): Mono<Result<GPS>> {
        // 通过文件部分获取文件内容
        return filePart.content()
            // 将文件内容映射为位置元数据
            .map { dataBuffer ->
                // 解析位置数据并获取经纬度，如果解析成功则创建GPS对象，否则返回空的GPS对象
                val gps = metaDataService.parseLocation(dataBuffer.asInputStream())
                    .map { geoLocation ->
                        GPS(geoLocation.longitude, geoLocation.latitude, null)
                    }.orElse(GPS())
                // 返回解析结果
                Result.ok(gps)
            }.next()
    }

    @Operation(summary = "获取图片创建时间")
    @PostMapping("/getCreateTime", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun getCreateTime(@RequestPart("file") filePart: FilePart): Mono<Result<String>> {
        // 通过文件部分获取文件内容
        return filePart.content()
            // 将文件内容映射为位置元数据
            .map { dataBuffer ->
                // 解析位置数据并获取经纬度，如果解析成功则创建GPS对象，否则返回空的GPS对象
                val createTime = metaDataService.parseCreatetime(dataBuffer.asInputStream())
                    .map { it.toFmtString() }
                    .orElse(null)
                // 返回解析结果
                Result.ok(createTime)
            }.next()
    }


}
