package dev.challenge.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages={"dev.challenge.api"})
@EnableJpaRepositories("dev.challenge.api.adapter.database.repository")
@EntityScan("dev.challenge.api.domain.model")
public class CustomerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerApiApplication.class, args);
	}

}
