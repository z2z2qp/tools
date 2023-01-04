package cn.zjujri.workday.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
            .info(new Info()
                .title("小工具集")
                .description("小工具集")
                .version("1.0.0")
                .license(new License().name("null"))
            )
            .externalDocs(new ExternalDocumentation().description("小工具集"));
    }
    
}
