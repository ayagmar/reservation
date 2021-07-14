package com.adservio.reservation.entities.dto;

import lombok.Data;

import java.util.Collection;
@Data
public class DepartmentDTO {
    private Long id;
    private String name;
    private Collection<RoomDTO> rooms;
}
