package cn.zjujri.workday

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
open class WorkdayApplication

fun main(args: Array<String>) {
    SpringApplication.run(WorkdayApplication::class.java, *args)
}
