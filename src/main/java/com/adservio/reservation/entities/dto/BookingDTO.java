package com.adservio.reservation.entities.dto;

import lombok.Data;

import java.util.Date;
@Data
public class BookingDTO {
    private Long id;
    Date StartDate;
    Date EndDate;
    private String description;
    private String bookingCode;
    private RoomDTO room;
    private UserDTO user;

}
