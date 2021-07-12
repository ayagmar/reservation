package com.adservio.reservation.dao;

import com.adservio.reservation.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookingRepository extends JpaRepository<Booking, Long> {

}