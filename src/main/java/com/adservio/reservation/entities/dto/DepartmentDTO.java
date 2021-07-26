package com.adservio.reservation.entities.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Collection;
@Data
public class DepartmentDTO {
    private Long id;
    private String name;
    @JsonIgnore
    private Collection<RoomDTO> rooms;
}
