package com.adservio.reservation.entities.dto;

import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import java.util.Date;
@Data
public class BookingDTO {
    private Long id;
    @FutureOrPresent
    Date StartDate;
    @FutureOrPresent
    Date EndDate;
    private String description;
    private String bookingCode;
    private RoomDTO room;
    private UserDTO user;

}
