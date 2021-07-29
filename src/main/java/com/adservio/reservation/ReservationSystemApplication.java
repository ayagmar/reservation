package com.adservio.reservation;

import com.adservio.reservation.dao.BookingRepository;
import com.adservio.reservation.entities.Booking;
import com.adservio.reservation.service.IReservationInitService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RequiredArgsConstructor
public class ReservationSystemApplication implements CommandLineRunner {

    private final IReservationInitService reservationInitService;
    private BookingRepository bookingRepository;


    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
        return modelMapper;
    }
    @Bean
    BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
 RestTemplate getRestTemplate(){
        return new RestTemplate();
 }




    public static void main(String[] args) {
        SpringApplication.run(ReservationSystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        reservationInitService.initRoles();
        reservationInitService.initUser();
        reservationInitService.initDepartment();
        reservationInitService.initRooms();
        reservationInitService.initReservation();



    }
}
