package com.adservio.reservation.service;

import com.adservio.reservation.dao.BookingRepository;
import com.adservio.reservation.dao.RoleRepository;
import com.adservio.reservation.dao.RoomRepository;
import com.adservio.reservation.dao.UserRepository;
import com.adservio.reservation.entities.Booking;
import com.adservio.reservation.entities.Room;
import com.adservio.reservation.entities.RoomStatus;
import com.adservio.reservation.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ReservationInitServiceImpl implements IReservationInitService{
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    Room r=new Room();
    User u=new User();
    Booking b =new Booking();
    @Override
    public void initRooms() {
    r.setCapacity(15);
    r.setNumber("S01");
    r.setRoomstate(RoomStatus.available);
    roomRepository.save(r);
    }

    @Override
    public void initUser() {
    u.setEmail("test@gmail.com");
    u.setFirstName("test");
    u.setPassword("odsdkssdkosd");
    u.setLastName("testa");
   userRepository.save(u);
    }

    @Override
    public void initReservation() {
    b.setDuration(4L);
    b.setDescription("BookingTest");
    b.setUser(u);
    b.setRoom(r);
    r.setRoomstate(RoomStatus.reserved);
    bookingRepository.save(b);
    }
}
