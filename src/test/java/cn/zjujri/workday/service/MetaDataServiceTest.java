package cn.zjujri.workday.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class MetaDataServiceTest {

    @Autowired
    private MetaDataService service;
    @Test
    void testParseLocation() throws FileNotFoundException {
        var tim = new File("C:\\Users\\zjbwi\\Desktop\\tim.JPG");
        var weixin = new File("C:\\Users\\zjbwi\\Desktop\\weixin.jpg");
        var dd = new File("C:\\Users\\zjbwi\\Desktop\\dd.JPG");
        var local = new File("C:\\Users\\zjbwi\\Desktop\\IMG_0789.HEIC");
        System.out.println("=======tim=======");
        service.parseLocation(new FileInputStream(tim));
        System.out.println("=======weix=========");
        service.parseLocation(new FileInputStream(weixin));
        System.out.println("=======dd=========");
        service.parseLocation(new FileInputStream(dd));
        System.out.println("=======local=========");
        service.parseLocation(new FileInputStream(local));
    }
}
