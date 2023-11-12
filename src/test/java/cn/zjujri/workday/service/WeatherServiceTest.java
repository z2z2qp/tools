package cn.zjujri.workday.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.zjujri.workday.module.CurrentWeather;

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
//        var result = service.forecastHistory(13.41,52.52,"2023-01-03","2023-01-04","Asia/Shanghai",List.of("temperature_2m","relativehumidity_2m","windspeed_10m"));
//        System.out.println(result);

    }

    @Test
    void testForecastcurrent() throws JsonMappingException, JsonProcessingException{
        var result = service.forecastCurrent(13.41,52.52,"Asia/Shanghai",true);
        var mappger = new ObjectMapper();
        Map<String,Object> value = mappger.readerForMapOf(Object.class).readValue(result);
        Map<String,Object> a = (Map<String,Object>)value.get("current_weather");
        System.out.println(a.get("winddirection").getClass());
        System.out.println(a.get("weathercode").getClass());
        Number temperature = (Number) a.get("temperature");
        Number windspeed = (Number) a.get("windspeed");
        Number winddirection = (Number) a.get("winddirection");
        Number weathercode = (Number) a.get("weathercode");
        String time = (String) a.get("time");
        var currentWeather = new CurrentWeather(temperature,windspeed,winddirection,weathercode,"",time);
        System.out.println(result);
        System.out.println(currentWeather);
    }
}
