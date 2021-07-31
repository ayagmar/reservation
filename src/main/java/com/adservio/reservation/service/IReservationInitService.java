package com.adservio.reservation.service;

import com.adservio.reservation.dao.*;
import com.adservio.reservation.entities.*;
import com.adservio.reservation.security.SecurityParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Stream;

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

        Room r3 = new Room();
        Stream.of("Room01", "Room02", "Room03", "Room04", "Room05", "Room06", "Room07", "Room08")
                .forEach(roomName -> {
                    Room room = new Room();
                    room.setName(roomName);
                    room.setDepartment(departmentRepository.findByName("Info"));
                    roomRepository.save(room);
                });

        r3.setName("Room09");
        r3.setDepartment(departmentRepository.findByName("Finance"));
        roomRepository.save(r3);
    }


    public void initUser() {
        User u = new User();
        User u2 = new User();
        User u3 = new User();
        u.setEmail("User1Email@gmail.com");
        u.setUsername("user1");
        u.setFirstName("Jhonny");
        u.setPassword("$2a$10$mktizboxOpE4tRtlvvDhaeH9tbXpeMppNmoJjakS6i7UshQmLS./y");
        u.setLastName("Depp");
        u.getRoles().add(roleRepository.findByRoleName(SecurityParams.ADMIN));
        u.getRoles().add(roleRepository.findByRoleName(SecurityParams.USER));
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

        String DateStart = "2021-07-29T08:00:00";
        String DateEnd = "2021-07-29T10:00:00";
        LocalDateTime dateS = LocalDateTime.parse(DateStart);
        LocalDateTime dateE = LocalDateTime.parse(DateEnd);
        Booking b = new Booking();
        Booking b2 = new Booking();
        Booking b3 = new Booking();
        b.setDescription("BookingTest1");
        b.setUser(userRepository.findByUsername("user1"));
        b.setRoom(roomRepository.findByName("Room01"));
        b.setStartDate(dateS);
        b.setEndDate(dateE);
        b.setCode(UUID.randomUUID().toString());
        //----//
        String DateStart2 = "2021-07-30T14:00:00";
        String DateEnd2 = "2021-07-30T15:00:00";

        LocalDateTime dateS2 = LocalDateTime.parse(DateStart2);
        LocalDateTime dateE2 = LocalDateTime.parse(DateEnd2);
        b2.setDescription("BookingTest2");
        b2.setUser(userRepository.findByUsername("user1"));
        b2.setRoom(roomRepository.findByName("Room02"));
        b2.setCode(UUID.randomUUID().toString());
        b2.setStartDate(dateS2);
        b2.setEndDate(dateE2);
        //----//
        String DateStart3 = "2021-07-29T10:00:00";
        String DateEnd3 = "2021-07-29T13:00:00";
        LocalDateTime dateS3 = LocalDateTime.parse(DateStart3);
        LocalDateTime dateE3 = LocalDateTime.parse(DateEnd3);
        b3.setDescription("BookingTest3");
        b3.setUser(userRepository.findByUsername("user2"));
        b3.setRoom(roomRepository.findByName("Room01"));
        b3.setCode(UUID.randomUUID().toString());
        b3.setStartDate(dateS3);
        b3.setEndDate(dateE3);

        bookingRepository.save(b);
        bookingRepository.save(b2);
        bookingRepository.save(b3);

    }


    public void initDepartment() {
        Department x = new Department();
        Department y = new Department();
        x.setName("Info");
        y.setName("Finance");
        departmentRepository.save(x);
        departmentRepository.save(y);
    }

    public void initRoles() {
        Role R = new Role();
        Role U = new Role();
        R.setRoleName(SecurityParams.ADMIN);
        U.setRoleName(SecurityParams.USER);
        roleRepository.save(R);
        roleRepository.save(U);
    }

}



