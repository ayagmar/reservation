package com.adservio.reservation.dto;

import com.adservio.reservation.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String FirstName;
    private String LastName;
    private String username;
    @Email
    @NotBlank(message = "Please enter the email")
    private String email;
    @Length(max=30,min=6)
    @NotBlank(message="Please enter the password")
    @JsonIgnore
    private String Password;
    @JsonIgnore
    private Collection<RoleDTO> roles = new ArrayList<>();
    @JsonIgnore
    private Collection<BookingDTO> bookings;

}
