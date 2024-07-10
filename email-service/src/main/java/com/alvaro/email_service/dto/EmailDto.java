package com.alvaro.email_service.dto;

import lombok.Data;

@Data
public class EmailDto {

    private String destinatario;

    private String asunto;

    private String mensaje;
}
