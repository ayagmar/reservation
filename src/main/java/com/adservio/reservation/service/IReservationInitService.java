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
public class IReservationInitService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private RoomRepository roomRepository;
    // private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    public void initRooms() {
        Room r =new Room();
        Room s =new Room();
        Room q=new Room();
        r.setName("S01");
        r.setDepartment(departmentRepository.getById(1L));
        roomRepository.save(r);
        s.setName("S02");
        s.setDepartment(departmentRepository.getById(1L));
        roomRepository.save(s);
        q.setName("S03");
        q.setDepartment(departmentRepository.getById(1L));
        roomRepository.save(q);
    }


    public void initUser() {
        User u =new User();
        u.setEmail("test@gmail.com");
        u.setFirstName("test");
        u.setPassword("password");
        u.setLastName("test12");
        userRepository.save(u);
    }


    public void initReservation() {
        Booking b=new Booking();
        b.setDescription("BookingTest");
        b.setUser(userRepository.getById(1L));
        b.setRoom(roomRepository.getById(1L));
        b.setCode(UUID.randomUUID().toString());
        bookingRepository.save(b);
    }



    public void initDepartment() {
        Department x=new Department();
        Department y=new Department();
        x.setName("Info");
        y.setName("Finance");
        departmentRepository.save(x);
        departmentRepository.save(y);
    }

}



