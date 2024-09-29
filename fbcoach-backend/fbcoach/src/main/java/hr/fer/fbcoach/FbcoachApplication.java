package hr.fer.fbcoach;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "hr.fer.fbcoach.repository")
@EntityScan(basePackages = "hr.fer.fbcoach.model")
public class FbcoachApplication {

	public static void main(String[] args) {
		SpringApplication.run(FbcoachApplication.class, args);
	}

}
