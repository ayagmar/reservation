package com.adservio.reservation.entities.dto;

import lombok.Data;

@Data
public class UserDTO {

    private Long id;
    private String FirstName;
    private String LastName;
    private String email;
    private String Password;

}
