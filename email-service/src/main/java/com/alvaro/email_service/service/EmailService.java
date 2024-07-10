package com.alvaro.email_service.service;

import com.alvaro.email_service.dto.EmailDto;
import com.alvaro.email_service.eventKafka.CreateUserEvent;
import com.alvaro.email_service.listener.KafkaConsumerListener;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.eclipse.angus.mail.util.MailConnectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Scanner;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(CreateUserEvent usuario) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("Msvc-Hotel");
            helper.setTo(usuario.getEmail());
            helper.setSubject("Bienvenido a la Plataforma: " + usuario.getUsername());

            String contenidoHtml = cargarContenidoHtml();

            helper.setText(contenidoHtml, true);

            javaMailSender.send(message);

            logger.info("Correo de bienvenida enviado con éxito a {}", usuario.getUsername());
        } catch (Exception e) {
            logger.error("Error al enviar el correo electrónico: {}", e.getMessage());
        }
    }

    private String cargarContenidoHtml() throws IOException {
        ClassPathResource resource = new ClassPathResource("templates/email.html");
        try (InputStream inputStream = resource.getInputStream()) {
            Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name());
            return scanner.useDelimiter("\\A").next();
        } catch (IOException e) {
            logger.error("Error al cargar el html: {}", e.getMessage());
            throw e;
        }
    }
}
