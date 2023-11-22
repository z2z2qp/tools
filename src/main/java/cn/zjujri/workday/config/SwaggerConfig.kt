package cn.zjujri.workday.config

import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("小工具集")
                    .description("小工具集")
                    .version("1.0.0")
                    .license(License().name("null"))
            )
            .externalDocs(ExternalDocumentation().description("小工具集"))
    }

}
