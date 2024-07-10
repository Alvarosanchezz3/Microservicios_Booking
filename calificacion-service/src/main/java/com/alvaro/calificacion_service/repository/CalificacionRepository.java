package com.alvaro.calificacion_service.repository;

import com.alvaro.calificacion_service.entity.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, String> {

    List<Calificacion> findByUsuarioId(String usuarioId);

    List<Calificacion> findByHotelId(String hotelId);
}
