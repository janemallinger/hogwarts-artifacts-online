package edu.tcu.cs.hogwartsartifactsonline;

import artifact.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HogwartsArtifactsOnlineApplication {

	public static void main(String[] args) {
		SpringApplication.run(HogwartsArtifactsOnlineApplication.class, args);
	}

	@Bean
	public IdWorker IdWorker() {
		return new IdWorker(1, 1);
	}


}
