package com.adservio.reservation.mapper;

import com.adservio.reservation.entities.Room;
import com.adservio.reservation.entities.dto.RoomDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoomConvert {

    public RoomDTO entityToDto(Room room) {

        ModelMapper mapper =new ModelMapper();

        return mapper.map(room,RoomDTO.class);

    }
    public List<RoomDTO> entityToDto(List<Room> rooms) {

        return	rooms.stream().map(this::entityToDto).collect(Collectors.toList());

    }


    public Room dtoToEntity(RoomDTO dto)
    {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(dto,Room.class);
    }

    public List<Room> dtoToEntity(List<RoomDTO> dto)
    {

        return dto.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }

}
