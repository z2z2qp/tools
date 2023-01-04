package cn.zjujri.workday.service;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest
@WebAppConfiguration
public class WeatherServiceTest {

    @Autowired
    private WeatherService service;

    @Test
    void testForecast() {
       //latitude=52.52&amp;hourly=temperature_2m%2Crelativehumidity_2m%2Cwindspeed_10m&amp;current_weather=true&amp;longitude=13.41
        //latitude=52.52&longitude=13.41&current_weather=true&hourly=temperature_2m,relativehumidity_2m,windspeed_10m
        // var map = new HashMap<String,List<String>>();
        // map.put("longitude", List.of("13.41"));
        // map.put("latitude", List.of("52.52"));
        // map.put("current_weather", List.of("true"));
        // map.put("hourly", List.of("temperature_2m","relativehumidity_2m","windspeed_10m"));
        var result = service.forecastHistory(13.41,52.52,"2023-01-03","2023-01-04","Asia/Shanghai",List.of("temperature_2m","relativehumidity_2m","windspeed_10m"));
        System.out.println(result);

    }

    @Test
    void testForecastcurrent(){
        var result = service.forecastCurrent(13.41,52.52,"Asia/Shanghai",false);
        System.out.println(result);
    }
}
