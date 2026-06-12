package com.example.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

import com.example.app.repository.HotelRepository;
import com.example.app.repository.ReservationRepository;
import com.example.app.repository.VoitureRepository;
import com.example.app.repository.impl.HotelRepositoryImpl;
import com.example.app.repository.impl.ReservationRepositoryImpl;
import com.example.app.repository.impl.VoitureImpl;

@Configuration
public class RepositoryConfig {

    @Bean
    public HotelRepository hotelRepository() {
        return new HotelRepositoryImpl();
    }

    @Bean
    public ReservationRepository reservationRepository() {
        return new ReservationRepositoryImpl();
    }

    @Bean
    public VoitureRepository voitureRepository() {
        return new VoitureImpl();
    }
}
