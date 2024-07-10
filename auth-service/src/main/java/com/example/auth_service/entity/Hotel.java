package com.example.auth_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {

    private String id;

    private String nombre;

    private String ubicacion;

    private String info;
}
