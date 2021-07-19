package com.adservio.reservation.entities.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Data
public class UserDTO {

    private Long id;
    private String FirstName;
    private String LastName;
    @Email
    @NotBlank(message = "Please enter the email")
    private String email;
    @Length(max=30,min=6)
    @NotBlank(message="Please enter the password")
    private String Password;
    private RoleDTO role;
    private Collection<BookingDTO> bookings;

}
