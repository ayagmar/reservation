package com.adservio.reservation.service;

import com.adservio.reservation.dao.*;
import com.adservio.reservation.entities.*;
import com.adservio.reservation.security.SecurityParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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
    SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a", Locale.ENGLISH);

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
        User u2=new User();
        User u3=new User();
        u.setEmail("User1Email@gmail.com");
        u.setUsername("user1");
        u.setFirstName("Jhonny");
        u.setPassword("$2a$10$mktizboxOpE4tRtlvvDhaeH9tbXpeMppNmoJjakS6i7UshQmLS./y");
        u.setLastName("Depp");
        u.getRoles().add(roleRepository.findByRoleName(SecurityParams.ADMIN));
        u.setActive(true);
        //-----//
        u2.setEmail("User2Email@gmail.com");
        u2.setUsername("user2");
        u2.setFirstName("Daniel");
        u2.setPassword("$2a$10$mktizboxOpE4tRtlvvDhaeH9tbXpeMppNmoJjakS6i7UshQmLS./y");
        u2.setLastName("Raddclif");
        u2.getRoles().add(roleRepository.findByRoleName(SecurityParams.USER));
        u2.setActive(true);
        //-----//
        u3.setEmail("User3Email@gmail.com");
        u3.setUsername("user3");
        u3.setFirstName("Adam");
        u3.setPassword("$2a$10$mktizboxOpE4tRtlvvDhaeH9tbXpeMppNmoJjakS6i7UshQmLS./y");
        u3.setLastName("Sandler");
        u3.getRoles().add(roleRepository.findByRoleName(SecurityParams.USER));
        u3.setActive(true);

        userRepository.save(u);
        userRepository.save(u2);
        userRepository.save(u3);
    }


    public void initReservation() throws ParseException {

        String DateStart = "23-07-2021 10:15:55 AM";
        String DateEnd = "23-07-2021 01:15:55 PM";

        Date dateS = formatter.parse(DateStart);
        Date dateE = formatter.parse(DateEnd);
        Booking b=new Booking();
        Booking b2=new Booking();
        Booking b3=new Booking();
        b.setDescription("BookingTest1");
        b.setUser(userRepository.findByUsername("user1"));
        b.setRoom(roomRepository.findByName("Room01"));
        b.setStartDate(dateS);
        b.setEndDate(dateE);
        b.setCode(UUID.randomUUID().toString());
        //----//
        String DateStart2 = "25-07-2021 02:15:55 AM";

        String DateEnd2 = "25-07-2021 05:15:55 PM";

        Date dateS2 = formatter.parse(DateStart2);
        Date dateE2 = formatter.parse(DateEnd2);
        b2.setDescription("BookingTest2");
        b2.setUser(userRepository.findByUsername("user1"));
        b2.setRoom(roomRepository.findByName("Room02"));
        b2.setCode(UUID.randomUUID().toString());
        b2.setStartDate(dateS2);
        b2.setEndDate(dateE2);
        //----//
        b3.setDescription("BookingTest3");
        b3.setUser(userRepository.findByUsername("user2"));
        b3.setRoom(roomRepository.findByName("Room01"));
        b3.setCode(UUID.randomUUID().toString());
        b3.setStartDate(dateS2);
        b3.setEndDate(dateE2);

        bookingRepository.save(b);
        bookingRepository.save(b2);
        bookingRepository.save(b3);


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
        Role U=new Role();
        R.setRoleName(SecurityParams.ADMIN);
        U.setRoleName(SecurityParams.USER);
        roleRepository.save(R);
        roleRepository.save(U);
    }

}



