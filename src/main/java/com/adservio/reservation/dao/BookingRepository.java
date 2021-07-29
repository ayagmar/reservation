package com.adservio.reservation.dao;

import com.adservio.reservation.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BookingRepository extends JpaRepository<Booking, Long> {

    Booking findByCode(String code);

}