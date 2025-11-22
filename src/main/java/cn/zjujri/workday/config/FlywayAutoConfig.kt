package cn.zjujri.workday.config

import org.flywaydb.core.Flyway
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

/**
 *
 *
 * @author 庄佳彬
 * @since 2025/11/22 20:27
 */

@Configuration
class FlywayAutoConfig {
    @Value($$"${spring.flyway.locations:classpath:sql}")
    private lateinit var locations: String

    @Value($$"${spring.flyway.baseline-on-migrate:true}")
    private var baselineOnMigrate: Boolean = true

    @Value($$"${spring.flyway.validate-on-migrate:true}")
    private var validateOnMigrate: Boolean = true

    @Bean
    fun flyway(dataSource: DataSource): Flyway {
        println("🔧 配置 Flyway...")

        val flyway = Flyway.configure()
            .dataSource(dataSource)
            .locations(locations)
            .baselineOnMigrate(baselineOnMigrate)
            .validateOnMigrate(validateOnMigrate)
            .load()
        val result = flyway.migrate()
        println("✅ Flyway 迁移完成，执行了 ${result.migrationsExecuted} 个脚本")
        return flyway
    }
}
