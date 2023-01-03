package cn.zjujri.workday;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class WorkdayApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkdayApplication.class, args);
	}

}
