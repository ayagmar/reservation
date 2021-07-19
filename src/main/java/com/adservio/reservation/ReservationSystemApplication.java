package com.adservio.reservation;

import com.adservio.reservation.service.IReservationInitService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ReservationSystemApplication implements CommandLineRunner {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
    @Bean
    BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private IReservationInitService reservationInitService;

    public static void main(String[] args) {
        SpringApplication.run(ReservationSystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        reservationInitService.initRoles();
        reservationInitService.initDepartment();
        reservationInitService.initRooms();
        reservationInitService.initUser();
        reservationInitService.initReservation();

    }
}
