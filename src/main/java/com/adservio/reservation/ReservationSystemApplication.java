package com.adservio.reservation;

import com.adservio.reservation.service.IReservationInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReservationSystemApplication implements CommandLineRunner {
    @Autowired
    private IReservationInitService reservationInitService;
    public static void main(String[] args) {
        SpringApplication.run(ReservationSystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        reservationInitService.initUser();
        reservationInitService.initRooms();
        reservationInitService.initReservation();
    }
}
