package com.adservio.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDateTime;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
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
