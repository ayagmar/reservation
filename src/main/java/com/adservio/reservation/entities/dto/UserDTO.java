package com.adservio.reservation.entities.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class UserDTO {

    private Long id;
    private String FirstName;
    private String LastName;
    private String email;
    private String Password;
    private Collection<BookingDTO> bookings;
}
