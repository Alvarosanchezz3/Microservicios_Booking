package com.example.auth_service.dto.auth;

import lombok.Data;

import java.io.Serializable;

@Data
public class TokenDto implements Serializable {

    private String jwt;
}
