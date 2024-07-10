package com.alvaro.hotel_service.config;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvLoader {

    public static void loadEnvVariables(String ruta) {
        Dotenv dotenv = null;

        try {
            dotenv = Dotenv.configure().filename(ruta).load();
        } catch (Exception e){
            System.out.println("Archivo .env de producción no encontrado, inicio de perfil de docker...");
        }

        // Obtener el perfil activo desde las variables de entorno si dotenv fue cargado exitosamente
        if (dotenv != null) {
            String activeProfile = dotenv.get("activeProfile");

            // Establecer la propiedad de perfil activo personalizada
            if (activeProfile != null) {
                System.setProperty("spring.profiles.active", activeProfile);
            }
        }
    }
}
