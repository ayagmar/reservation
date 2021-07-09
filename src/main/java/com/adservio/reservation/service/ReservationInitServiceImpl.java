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

import javax.transaction.Transactional;
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
    @Override
    public void initRooms() {

    r.setCapacity(15);
    r.setNumber("S01");
    r.setRoomstate(RoomStatus.available);
    roomRepository.save(r);
    }
    User u=new User();
    @Override
    public void initUser() {

    u.setEmail("test@gmail.com");
    u.setFirstName("test");
    u.setPassword("odsdkssdkosd");
    u.setLastName("testa");
   //userRepository.save(u);
    }

    @Override
    public void initReservation() {
    Booking b =new Booking();
    b.setDuration(4L);
    b.setDescription("BookingTest");
    b.setUser(u);
    //bookingRepository.save(b);
    }
}
