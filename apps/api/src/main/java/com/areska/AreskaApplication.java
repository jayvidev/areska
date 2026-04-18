package com.areska;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "Areska API",
        version = "1.0.0",
        description = "Backend API for Areska e-commerce platform, providing services for product management.",
        license = @License(name = "MIT", url = "https://opensource.org/licenses/MIT")
    )
)
public class AreskaApplication {
	public static void main(String[] args) {
		SpringApplication.run(AreskaApplication.class, args);
	}
}
