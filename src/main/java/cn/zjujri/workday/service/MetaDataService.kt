package cn.zjujri.workday.service

import cn.zjujri.workday.util.toLocalDateTime
import com.drew.imaging.ImageMetadataReader
import com.drew.imaging.ImageProcessingException
import com.drew.lang.GeoLocation
import com.drew.metadata.exif.ExifSubIFDDirectory
import com.drew.metadata.exif.GpsDirectory
import org.springframework.stereotype.Service
import java.io.IOException
import java.io.InputStream
import java.time.LocalDateTime
import java.util.*

/**
 * MetaDataService类是一个用于解析图片地理位置信息的服务类。
 *
 * @@Service 注解表示这是一个服务类，用于被Spring容器管理。
 */
@Service
class MetaDataService {

    /**
     * 解析图片地理位置信息。
     *
     * @param image 图片的输入流
     * @return 返回图片的地理位置信息，如果解析失败则返回空
     */
    fun parseLocation(image: InputStream): Optional<GeoLocation> {
        // 解析图片的元数据
        try {
            val metaData = ImageMetadataReader.readMetadata(image)
            // 获取GPS信息
            val gps = metaData.getFirstDirectoryOfType(GpsDirectory::class.java)
            // 获取第一个GPS目录的地理位置信息
            return Optional.ofNullable(gps.geoLocation)
        } catch (e: ImageProcessingException) {
            // 处理图片处理异常
            e.printStackTrace()
        } catch (e: IOException) {
            // 处理IO异常
            e.printStackTrace()
        }
        // 返回空的地理位置信息
        return Optional.empty()
    }

    /**
     * 解析图片创建时间
     *
     * @param image 图片的输入流
     * @return 返回图片的创建时间，如果解析失败则返回空
     */
    fun parseCreatetime(image: InputStream): Optional<LocalDateTime> {
        val metaData = ImageMetadataReader.readMetadata(image)
//        val exifIFD0Directory = metaData.directories //getFirstDirectoryOfType(ExifIFD0Directory::class.java)
//        exifIFD0Directory.forEach {directory->
//            directory.tags.forEach {tag ->
//                println(tag)
//                println(tag.tagName)
//                println(tag.tagType)
//            }
//        }
        val exifSubIFD: ExifSubIFDDirectory? = metaData.getFirstDirectoryOfType(ExifSubIFDDirectory::class.java)
        return if (exifSubIFD == null) {
            Optional.empty()
        } else {
            exifSubIFD.tags
                .stream()
                .filter { it.tagName == "Date/Time Original" }
                .findFirst()
                .map {
                    it.description
                }.map {
                    it.toLocalDateTime("yyyy:MM:dd HH:mm:ss")
                }
        }
    }

}
