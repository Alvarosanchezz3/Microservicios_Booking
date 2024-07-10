package com.alvaro.email_service.eventKafka;

import lombok.Data;

@Data
public class CreateUserEvent {
    private String name;

    private String username;

    private String email;
}
