# Arquitectura de Microservicios para Reserva de Hoteles 🏨

¡Bienvenidos al repositorio de la arquitectura de microservicios para la reserva de hoteles! Este proyecto está desarrollado en Java utilizando Spring y está diseñado para ser escalable, eficiente y resiliente.

## Descripción del Proyecto

Este proyecto implementa una arquitectura de microservicios para gestionar reservas de hoteles. Cada servicio es independiente y se comunica con los demás a través de diversas tecnologías y herramientas modernas.

### Componentes Clave

1. **API Gateway**: Punto de entrada para todas las solicitudes, manejando la autenticación y enrutamiento. Controla el acceso a los recursos según los permisos o roles del usuario.
2. **Servicio Auth**: Maneja la autenticación de usuarios con JWT y codifica contraseñas usando PasswordEncoder.
3. **Servicio de Usuarios, Hoteles y Calificaciones**: Cada servicio maneja su propio conjunto de datos en bases de datos MySQL y PostgreSQL.
4. **Comunicación Asíncrona con Kafka**: Mejora la eficiencia y escalabilidad mediante la comunicación asíncrona. Por ejemplo, el Servicio de Usuarios notifica al Servicio de Email mediante Kafka cuando se crea un nuevo usuario, proporcionando la información necesaria para enviar un correo de bienvenida.
5. **Servicio de Email**: Notifica a los usuarios sobre sus reservas y otros eventos importantes.
6. **Config Server, Eureka y Resilience4j**: Utilizados para configuración centralizada, descubrimiento de servicios y tolerancia a fallos, respectivamente.
7. **Monitorización y Contenerización**: Zipkin para la trazabilidad y monitorización, y Docker para la contenerización de los servicios.

### Tecnologías Utilizadas

- **Spring Boot & Spring Cloud**: Para la construcción y despliegue de microservicios.
- **JWT**: Para autenticación segura.
- **PasswordEncoder**: Para la codificación de contraseñas y asegurar la información sensible.
- **Kafka**: Para la comunicación asíncrona entre microservicios.
- **MySQL & PostgreSQL**: Bases de datos relacionales para el almacenamiento de datos.
- **Docker**: Para la contenerización y despliegue en cualquier entorno.
- **Zipkin**: Para la trazabilidad y monitorización de solicitudes.
- **Resilience4j**: Para la implementación de patrones de resiliencia.

## Configuración

Para configurar el proyecto, es necesario crear un archivo `.env` en la raíz del repositorio con los siguientes valores:

- MAIL_USERNAME=your_email_username
- MAIL_PASSWORD=your_email_password
- MYSQL_ROOT_PASSWORD=your_mysql_root_password
- POSTGRES_PASSWORD=your_postgres_password
- EUREKA_USERNAME=your_eureka_username
- EUREKA_PASSWORD=your_eureka_password
- secret_key=your_secret_key
- activeProfile=your_active_profile

## Diagrama de la arquitectura

https://github.com/Alvarosanchezz3/Microservicios_Booking/assets/99328696/d5d95507-81c6-4afa-a876-8741a0ecf2a5
