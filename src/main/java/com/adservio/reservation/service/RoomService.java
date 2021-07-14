package com.adservio.reservation.service;

import com.adservio.reservation.dao.RoomRepository;
import com.adservio.reservation.entities.Room;
import com.adservio.reservation.entities.dto.RoomDTO;
import com.adservio.reservation.mapper.RoomConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
   private RoomConvert converter;

    public List<RoomDTO> listAll(){

        return converter.entityToDto(roomRepository.findAll());
    }

    public RoomDTO getById(Long id){

        return converter.entityToDto(roomRepository.findById(id).orElse(null));
    }

    public List<RoomDTO> saveRooms(List<RoomDTO> roomsdto){
        List<Room> rooms=converter.dtoToEntity(roomsdto);
        rooms=roomRepository.saveAll(rooms);
        return converter.entityToDto(rooms);

    }


    public RoomDTO getRoomByName(String name) {
        return converter.entityToDto(roomRepository.findByName(name));
    }

    public RoomDTO save(RoomDTO roomdto){
    Room room=converter.dtoToEntity(roomdto);
    room=roomRepository.save(room);
    return converter.entityToDto(room);
    }




public String deleteRoom(Long id){
        roomRepository.deleteById(id);
        return "Deleted successfully";
}

public Room updateRoom(Room room){
        Room eroom=roomRepository.findById(room.getId()).orElse(null);
        assert eroom != null;
        eroom.setName(room.getName());
        return roomRepository.save(eroom);
}
}
