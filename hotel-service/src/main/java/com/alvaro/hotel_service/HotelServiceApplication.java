package com.alvaro.hotel_service;

import com.alvaro.hotel_service.config.EnvLoader;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HotelServiceApplication {

	public static void main(String[] args) {
		EnvLoader.loadEnvVariables(".env");
		SpringApplication.run(HotelServiceApplication.class, args);
	}
}