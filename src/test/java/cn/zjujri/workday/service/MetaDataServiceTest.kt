package cn.zjujri.workday.service

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException

@SpringBootTest
class MetaDataServiceTest {
    @Autowired
    private val service: MetaDataService? = null


    @Test
    @Throws(FileNotFoundException::class)
    fun testParseCreatetime() {
        val file = File("C:\\Users\\zjbwi\\Desktop\\IMG_1431.JPG")
        val result = service!!.parseCreatetime(FileInputStream(file))
        result.ifPresent {
            println(it)
        }
    }

    @Test
    @Throws(FileNotFoundException::class)
    fun testParseLocation() {
        val tim = File("C:\\Users\\zjbwi\\Desktop\\tim.JPG")
        val weixin = File("C:\\Users\\zjbwi\\Desktop\\weixin.jpg")
        val dd = File("C:\\Users\\zjbwi\\Desktop\\dd.JPG")
        val local = File("C:\\Users\\zjbwi\\Desktop\\IMG_1431.JPG")
        println("=======tim=======")
        service!!.parseLocation(FileInputStream(tim))
            .ifPresent {
                println(it)
            }
        println("=======weix=========")
        service.parseLocation(FileInputStream(weixin))
            .ifPresent {
                println(it)
            }
        println("=======dd=========")
        service.parseLocation(FileInputStream(dd))
            .ifPresent {
                println(it)
            }
        println("=======local=========")
        service.parseLocation(FileInputStream(local))
            .ifPresent {
                println(it)
            }
    }
}
