package com.adservio.reservation.service;

import com.adservio.reservation.dao.BookingRepository;
//import com.adservio.reservation.dao.RoleRepository;
import com.adservio.reservation.dao.DepartmentRepository;
import com.adservio.reservation.dao.RoomRepository;
import com.adservio.reservation.dao.UserRepository;
import com.adservio.reservation.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import javax.transaction.Transactional;
import java.util.UUID;


@Service
@Transactional
public class ReservationInitServiceImpl implements IReservationInitService{
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private RoomRepository roomRepository;
   // private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Override
    public void initRooms() {
     Room r =new Room();
     Room s =new Room();
     Room q=new Room();
    r.setName("S01");
    r.setRoomstate(RoomStatus.available);
    r.setDepartment(departmentRepository.getById(1L));
    roomRepository.save(r);
    s.setName("S02");
    s.setRoomstate(RoomStatus.available);
    s.setDepartment(departmentRepository.getById(1L));
     roomRepository.save(s);
    q.setName("S03");
    q.setRoomstate(RoomStatus.available);
    q.setDepartment(departmentRepository.getById(1L));
    roomRepository.save(q);
    }

    @Override
    public void initUser() {
     User u =new User();
    u.setEmail("test@gmail.com");
    u.setFirstName("test");
    u.setPassword("odsdkssdkosd");
    u.setLastName("testa");
    userRepository.save(u);
    }

    @Override
    public void initReservation() {
     Booking b=new Booking();
    b.setDescription("BookingTest");
    b.setUser(userRepository.getById(1L));
    b.setRoom(roomRepository.getById(1L));
    roomRepository.getById(1L).setRoomstate(RoomStatus.reserved);
    b.setBookingCode(UUID.randomUUID().toString());
    bookingRepository.save(b);
    }



 @Override
 public void initDepartment() {
     Department x=new Department();
     Department y=new Department();
     x.setName("Info");
     y.setName("Finance");
     departmentRepository.save(x);
     departmentRepository.save(y);
 }

}
