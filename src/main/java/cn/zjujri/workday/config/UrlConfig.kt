package cn.zjujri.workday.config

import cn.zjujri.workday.service.WeatherService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

@Configuration
class UrlConfig {

    @Bean
    fun weatherService(): WeatherService {
        // https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&current_weather=true&hourly=temperature_2m,relativehumidity_2m,windspeed_10m
        val client = WebClient.builder().baseUrl("https://api.open-meteo.com").build()
        val factory = HttpServiceProxyFactory.builderFor(WebClientAdapter.create(client)).build()
        val clazz: Class<WeatherService> = WeatherService::class.java
        return factory.createClient(clazz)
    }

}
