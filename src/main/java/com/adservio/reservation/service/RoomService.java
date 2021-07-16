package com.adservio.reservation.service;

import com.adservio.reservation.dao.RoomRepository;
import com.adservio.reservation.entities.Room;
import com.adservio.reservation.entities.dto.RoomDTO;
import com.adservio.reservation.exception.NotFoundException;
import com.adservio.reservation.mapper.RoomConvert;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class RoomService {

    private final RoomRepository roomRepository;

    private final RoomConvert converter;

    public RoomService(RoomRepository roomRepository, RoomConvert converter) {
        this.roomRepository = roomRepository;
        this.converter = converter;
    }

    public List<RoomDTO> listAll() {

        return converter.entityToDto(roomRepository.findAll());
    }

    public RoomDTO getById(Long id) throws NotFoundException {

        Optional<Room> room = roomRepository.findById(id);
        if (room.isEmpty()) throw new NotFoundException("Room Not Available");
        return converter.entityToDto(room.get());
    }

    public List<RoomDTO> saveRooms(List<RoomDTO> roomsdto) {
        List<Room> rooms = converter.dtoToEntity(roomsdto);
        rooms = roomRepository.saveAll(rooms);
        return converter.entityToDto(rooms);

    }


    public RoomDTO getRoomByName(String name) {
        return converter.entityToDto(roomRepository.findByName(name));
    }

    public RoomDTO save(RoomDTO roomdto) {
        Room room = converter.dtoToEntity(roomdto);
        room = roomRepository.save(room);
        return converter.entityToDto(room);
    }


    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }

    public RoomDTO updateRoom(Long id, RoomDTO roomDTO) {

        Room roomDB = roomRepository.findById(id).get();

        if (Objects.nonNull(roomDTO.getName()) &&
                !"".equalsIgnoreCase(roomDTO.getName())) {
            roomDB.setName(roomDTO.getName());
        }
        roomRepository.save(roomDB);


        return converter.entityToDto(roomDB);
    }

}
