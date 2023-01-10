package cn.zjujri.workday.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.zjujri.workday.module.GPS;
import cn.zjujri.workday.module.Result;
import cn.zjujri.workday.service.MetaDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name="图片")
@RestController
@RequestMapping("image")
public class ImageLocation {

    private final MetaDataService metaDataService;

    public ImageLocation(MetaDataService metaDataService) {
        this.metaDataService = metaDataService;
    }

    @Operation(summary = "获取经纬度")
    @PostMapping("/getLocation")
    public Result<GPS> getLocation(MultipartFile file) throws IOException{
        var optional = metaDataService.parseLocation(file.getInputStream());
        var gps = optional.map(t -> {
            var g = new GPS();
            g.setLatitude(t.getLatitude());
            g.setLongitude(t.getLongitude());
            return g;
        }).orElse(new GPS());
        return Result.ok(gps);
    }


    
}
