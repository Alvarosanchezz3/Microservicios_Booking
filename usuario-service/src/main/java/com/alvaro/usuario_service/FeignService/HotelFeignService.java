package com.alvaro.usuario_service.FeignService;

import com.alvaro.usuario_service.entity.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "hotel-service")
public interface HotelFeignService {

    @GetMapping("/hoteles/{hotelId}")
    Hotel obtenerHotel(@PathVariable String hotelId);
}
