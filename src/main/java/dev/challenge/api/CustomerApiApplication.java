package dev.challenge.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Generated
@OpenAPIDefinition(info = @Info(title = "Customer Api", version = "1.0"))
@SpringBootApplication(scanBasePackages={"dev.challenge.api"})
@EnableJpaRepositories("dev.challenge.api.adapter.database.repository")
@EntityScan("dev.challenge.api.domain.model")
public class CustomerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerApiApplication.class, args);
	}

}
