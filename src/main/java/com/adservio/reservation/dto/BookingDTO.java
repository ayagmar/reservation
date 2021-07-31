package com.adservio.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private Long id;
    @FutureOrPresent
    LocalDateTime StartDate;
    @FutureOrPresent
    LocalDateTime EndDate;
    private String description;
    private String code;
    private RoomDTO room;
    private UserDTO user;

}
