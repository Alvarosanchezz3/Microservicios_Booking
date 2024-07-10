package com.alvaro.usuario_service.entity;

import jakarta.persistence.Entity;
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
