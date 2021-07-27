package com.adservio.reservation.dto;



import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;


@Data
public class RoomDTO {
    private Long id;
    private String name;
    @JsonIgnore
    private DepartmentDTO department;


}
