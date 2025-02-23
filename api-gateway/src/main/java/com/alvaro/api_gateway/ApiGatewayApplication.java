package com.alvaro.api_gateway;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

	public static void main(String[] args) {
		Dotenv dotenv = null;

		try {
			dotenv = Dotenv.configure().filename("/.env").load();
		} catch (Exception e){
			System.out.println("Archivo .env de producción no encontrado, inicio de perfil de docker...");
		}

		// Obtener el perfil activo desde las variables de entorno si dotenv fue cargado exitosamente
		if (dotenv != null) {
			String activeProfile = dotenv.get("activeProfile");

			// Establecer la propiedad de perfil activo personalizada
			if (activeProfile != null) {
				System.setProperty("activeProfile", activeProfile);
			}
		}
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
}
