package com.alvaro.calificacion_service.service;

import com.alvaro.calificacion_service.entity.Calificacion;

import java.util.List;

public interface CalificacionService {

    Calificacion saveCalificacion (Calificacion calificacion);

    List<Calificacion> getAll();

    List<Calificacion> getCalificacionesByUsuarioId(String usuarioId);

    List<Calificacion> getCalificacionesByHotelId(String hotelId);

}
