package cn.zjujri.workday.service

import com.drew.imaging.ImageMetadataReader
import com.drew.imaging.ImageProcessingException
import com.drew.lang.GeoLocation
import com.drew.metadata.exif.GpsDirectory
import org.springframework.stereotype.Service
import java.io.IOException
import java.io.InputStream
import java.util.*

@Service
open class MetaDataService {

    fun parseLocation(image: InputStream): Optional<GeoLocation> {
        try {
            val metaData = ImageMetadataReader.readMetadata(image)
            val gps = metaData.getDirectoriesOfType(GpsDirectory::class.java)
            return gps.stream().findFirst().map(GpsDirectory::getGeoLocation)
        } catch (e: ImageProcessingException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return Optional.empty()
    }
}
