package com.adservio.reservation.service;

import com.adservio.reservation.dao.BookingRepository;
import com.adservio.reservation.dao.RoomRepository;
import com.adservio.reservation.dto.BookingDTO;
import com.adservio.reservation.dto.UserDTO;
import com.adservio.reservation.entities.Booking;
import com.adservio.reservation.entities.Room;
import com.adservio.reservation.dto.RoomDTO;
import com.adservio.reservation.entities.User;
import com.adservio.reservation.exception.NotFoundException;
import com.adservio.reservation.mapper.BookingConvert;
import com.adservio.reservation.mapper.RoomConvert;
import com.adservio.reservation.mapper.UserConvert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    private final RoomConvert converter;
    private final BookingConvert bookingConvert;
    private final UserConvert userConvert;


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

    public Collection<UserDTO> GetUsersByRoom(Long roomid) throws NotFoundException {
        RoomDTO roomDTO = getById(roomid);
        Room room = converter.dtoToEntity(roomDTO);
        Collection<Booking> bookings = room.getBookings();
        List<User> users = new ArrayList<>();
        for (Booking booking : bookings) {
            users.add(booking.getUser());
        }
        return userConvert.entityToDto(users);
    }
    public RoomDTO getRoomByName(String name) throws NotFoundException {
        if(name.isEmpty()) throw new NotFoundException("Insert a name to find room");
        if(roomRepository.findByName(name)==null) throw new NotFoundException("Room not found");
        return converter.entityToDto(roomRepository.findByName(name));
    }

    public RoomDTO save(RoomDTO roomdto) {
        Room room = converter.dtoToEntity(roomdto);
        room = roomRepository.save(room);
        return converter.entityToDto(room);
    }

    public List<RoomDTO>FindAvailable(LocalDateTime datestart,LocalDateTime dateend){
        if(dateend==null){
            dateend=datestart.plusMonths(1);
        }
        List<Room> availrooms=roomRepository.findMeetingRoomAvailable(datestart,dateend);
        return converter.entityToDto(availrooms);
    }

    public Collection<BookingDTO> listBookByRoom(Long roomid) throws NotFoundException {
        RoomDTO roomDTO=getById(roomid);
        Room room=converter.dtoToEntity(roomDTO);
        Collection<Booking> list=room.getBookings();
        return bookingConvert.entityToDto(list);
    }




    public RoomDTO GetLastReservedRoom(){
        List<Booking> bookings =bookingRepository.findAll();
        Booking latest =bookings.get(bookings.size()-1);
        return converter.entityToDto(latest.getRoom());
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
