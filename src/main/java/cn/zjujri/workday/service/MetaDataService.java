package cn.zjujri.workday.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.exif.GpsDirectory;

@Service
public class MetaDataService {

    public Optional<GeoLocation> parseLocation(InputStream image) {
        try {
            var metaData = ImageMetadataReader.readMetadata(image);
            var gps = metaData.getDirectoriesOfType(GpsDirectory.class);
            return gps.stream().findFirst().map(GpsDirectory::getGeoLocation);
        } catch (ImageProcessingException | IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
