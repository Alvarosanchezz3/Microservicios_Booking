package com.alvaro.email_service.listener;


import com.alvaro.email_service.dto.EmailDto;
import com.alvaro.email_service.eventKafka.CreateUserEvent;
import com.alvaro.email_service.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;


@Configuration
public class KafkaConsumerListener {

    private Logger logger = LoggerFactory.getLogger(KafkaConsumerListener.class);

    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "email-topic", groupId = "emailGroup")
    public void listenerAndSendEmail(String message) {
        logger.info("Usuario nuevo creado en el usuario-service: " + message);

        // Convierte el mensaje de Kafka en un dto de Usuario
        CreateUserEvent userEvent = convertStringToCreateUserEvent(message);

        // Se envía un correo al usuario dandole la bienvenida
        emailService.sendMail(userEvent);
    }

    // Método para pasar el mensaje de Kafka a un CreateUserEvent (dto de Usuario)
    private CreateUserEvent convertStringToCreateUserEvent(String message) {
        String[] parts = message.split(",");

        CreateUserEvent userEvent = new CreateUserEvent();
        userEvent.setName(parts[0].split("=")[1].trim());
        userEvent.setUsername(parts[1].split("=")[1].trim());
        userEvent.setEmail(parts[2].split("=")[1].trim().replaceAll("[)\"]", ""));

        return userEvent;
    }
}
