package cn.zjujri.workday.service

import cn.zjujri.workday.module.CurrentWeather
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.web.WebAppConfiguration

/**
 *
 *
 * @author 庄佳彬
 * @since 2023/11/21 11:11
 */
@SpringBootTest
@WebAppConfiguration
class WeatherServiceTest {
    @Autowired
    private val service: WeatherService? = null

    @Test
    fun testForecast() {
        val result = runBlocking {
            async {
                service!!.forecastHistory(
                    13.41,
                    52.52,
                    "2023-01-03",
                    "2023-01-04",
                    "Asia/Shanghai",
                    listOf("temperature_2m", "relativehumidity_2m", "windspeed_10m")
                ).block()
            }.await()
        }
        println(result)
    }

    @Test
    fun testForecastcurrent() {
        val result = runBlocking {
            async {
                service!!.forecastCurrent(
                    13.41,
                    52.52,
                    "Asia/Shanghai",
                    true
                ).block()
            }.await()
        }
        val mapper = ObjectMapper()
        val value = mapper.readerForMapOf(Any::class.java).readValue<Map<String, Any>>(result)
        val a = value["current_weather"] as Map<*, *>
        println(a["winddirection"]?.javaClass)
        println(a["weathercode"]?.javaClass)
        val temperature = a["temperature"] as Number
        val windspeed = a["windspeed"] as Number
        val winddirection = a["winddirection"] as Number
        val weathercode = a["weathercode"] as Number
        val time = a["time"] as String
        val currentWeather = CurrentWeather(temperature, windspeed, winddirection, weathercode, "", time)
        println(currentWeather)
    }
}