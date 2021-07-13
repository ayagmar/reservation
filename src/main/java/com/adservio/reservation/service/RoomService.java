package com.adservio.reservation.service;

import com.adservio.reservation.dao.RoomRepository;
import com.adservio.reservation.entities.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<Room> listAll(){
    return roomRepository.findAll();
    }

    public Room get(Long id){
        return roomRepository.findById(id).orElse(null);
    }

    public List<Room> saveRooms(List<Room> rooms){
        return roomRepository.saveAll(rooms);
    }


    public Room getRoomByName(String name) {
        return roomRepository.findByName(name);
    }

    public Room save(Room room){
        return roomRepository.save(room);
    }




public String deleteRoom(Long id){
        roomRepository.deleteById(id);
        return "Deleted successfully";
}

public Room updateRoom(Room room){
        Room eroom=roomRepository.findById(room.getId()).orElse(null);
    assert eroom != null;
    eroom.setName(room.getName());
        eroom.setRoomstate(room.getRoomstate());
        return roomRepository.save(eroom);
}

}
