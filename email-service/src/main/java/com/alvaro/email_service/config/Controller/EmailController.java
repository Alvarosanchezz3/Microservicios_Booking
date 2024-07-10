package com.alvaro.email_service.config.Controller;

import com.alvaro.email_service.eventKafka.CreateUserEvent;
import com.alvaro.email_service.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/enviar-correo")
    public String enviarCorreo(@RequestBody CreateUserEvent usuario) {
        try {
            emailService.sendMail(usuario);
            return "Correo enviado exitosamente a " + usuario.getEmail();
        } catch (Exception e) {
            return "Error al enviar el correo: " + e.getMessage();
        }
    }
}
