package cn.zjujri.workday.config

import org.flywaydb.core.Flyway
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class FlywayConfig {

    @Value("\${spring.flyway.locations:classpath:sql}")
    private lateinit var locations: String

    @Value("\${spring.flyway.baseline-on-migrate:true}")
    private var baselineOnMigrate: Boolean = true

    @Value("\${spring.flyway.validate-on-migrate:true}")
    private var validateOnMigrate: Boolean = true

    @Bean(initMethod = "migrate")
    fun flyway(dataSource: DataSource): Flyway {
        return Flyway.configure()
            .dataSource(dataSource)
            .locations(locations)
            .baselineOnMigrate(baselineOnMigrate)
            .validateOnMigrate(validateOnMigrate)
            .load()
    }
}
