package com.adservio.reservation.service;

import com.adservio.reservation.dao.*;

import com.adservio.reservation.entities.*;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.UUID;
@Service
@Transactional
@RequiredArgsConstructor
public class IReservationInitService {
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final RoleRepository roleRepository;

    public void initRooms() {
        Room r =new Room();
        Room s =new Room();
        Room q=new Room();
        r.setName("Room01");
        r.setDepartment(departmentRepository.findByName("Info"));
        roomRepository.save(r);
        s.setName("Room02");
        s.setDepartment(departmentRepository.findByName("Info"));
        roomRepository.save(s);
        q.setName("Room03");
        q.setDepartment(departmentRepository.findByName("Finance"));
        roomRepository.save(q);
    }


    public void initUser() {
        User u =new User();
        u.setEmail("test@gmail.com");
        u.setUsername("user1");
        u.setFirstName("test");
        u.setPassword("$2a$10$mktizboxOpE4tRtlvvDhaeH9tbXpeMppNmoJjakS6i7UshQmLS./y");
        u.setLastName("test12");
        u.getRoles().add(roleRepository.findByRoleName("ADMIN"));
        u.setActive(true);
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

    public void initRoles(){
        Role R=new Role();
        R.setRoleName("ADMIN");
        roleRepository.save(R);
        Role U=new Role();
        U.setRoleName("USER");
        roleRepository.save(U);

    }

}



