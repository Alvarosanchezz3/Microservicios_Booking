package com.example.eureka_server;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication{

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().filename("/.env").load();

		// Obtener el perfil activo desde las variables de entorno
		String activeProfile = dotenv.get("activeProfile");

		// Establecer la propiedad de perfil activo personalizada
        if (activeProfile != null) {
            System.setProperty("activeProfile", activeProfile);
        }
		SpringApplication.run(EurekaServerApplication.class, args);
	}
}