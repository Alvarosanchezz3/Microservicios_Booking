package com.example.auth_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Calificacion {

    private String id;

    private String usuarioId;

    private String hotelId;

    private int calificacion;

    private String observaciones;

    private Hotel hotel;
}
