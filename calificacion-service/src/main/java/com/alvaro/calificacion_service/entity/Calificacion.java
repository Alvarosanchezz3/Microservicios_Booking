package com.alvaro.calificacion_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Calificacion {

    @Id
    private String id;

    private String usuarioId;

    private String hotelId;

    private int calificacion;

    private String observaciones;
}
