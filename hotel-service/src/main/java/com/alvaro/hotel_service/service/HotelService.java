package com.alvaro.hotel_service.service;

import com.alvaro.hotel_service.entity.Hotel;

import java.util.List;

public interface HotelService {

    Hotel saveHotel (Hotel hotel);

    List<Hotel> getAll();

    Hotel getHotel (String id);

}
