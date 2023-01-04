package cn.zjujri.workday.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import cn.zjujri.workday.service.WeatherService;

@Configuration
public class UrlConfig {

    @Bean
    public WeatherService weatherService() {
        // https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&current_weather=true&hourly=temperature_2m,relativehumidity_2m,windspeed_10m
        var client = WebClient.builder().baseUrl("https://api.open-meteo.com").build();
        var factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();
        return factory.createClient(WeatherService.class);
    }

}
