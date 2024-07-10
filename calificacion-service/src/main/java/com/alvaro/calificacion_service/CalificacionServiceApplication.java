package com.alvaro.calificacion_service;

import com.alvaro.calificacion_service.config.EnvLoader;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CalificacionServiceApplication {

	public static void main(String[] args) {
		EnvLoader.loadEnvVariables(".env");
		SpringApplication.run(CalificacionServiceApplication.class, args);
	}
}
