package com.adservio.reservation.entities.dto;

import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.FutureOrPresent;
import java.util.Date;
@Data
public class BookingDTO {
    private Long id;
    @FutureOrPresent
    @Temporal(TemporalType.DATE)
    Date StartDate;
    @Temporal(TemporalType.DATE)
    @FutureOrPresent
    Date EndDate;
    private String description;
    private String bookingCode;
    private RoomDTO room;
    private UserDTO user;

}
