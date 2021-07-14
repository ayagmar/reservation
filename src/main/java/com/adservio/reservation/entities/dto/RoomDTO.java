package com.adservio.reservation.entities.dto;



import lombok.Data;


@Data
public class RoomDTO {
    private Long id;
    private String name;
    private DepartmentDTO department;


}
