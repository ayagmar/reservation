package com.adservio.reservation.service;

import com.adservio.reservation.dao.RoleRepository;
import com.adservio.reservation.dao.RoomRepository;
import com.adservio.reservation.dao.UserRepository;
import com.adservio.reservation.dto.BookingDTO;
import com.adservio.reservation.entities.Booking;
import com.adservio.reservation.entities.Role;
import com.adservio.reservation.entities.User;
import com.adservio.reservation.dto.UserDTO;
import com.adservio.reservation.exception.NotFoundException;
import com.adservio.reservation.mapper.BookingConvert;
import com.adservio.reservation.mapper.UserConvert;
import com.adservio.reservation.security.SecurityParams;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final RoleRepository roleRepository;
    private final UserConvert userconverter;
    private final BookingConvert bookingConvert;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;



    public List<UserDTO> listAll(){
        return userconverter.entityToDto(userRepository.findAll());
    }

    public UserDTO getById(Long id) throws NotFoundException{
        Optional<User> user=userRepository.findById(id);
        if(user.isEmpty()) throw new NotFoundException("User Not Available");
        return userconverter.entityToDto(user.get());
    }

    public UserDTO GetUserByEmail(String email) {
        return userconverter.entityToDto(userRepository.findByEmail(email));
    }

    public User GetUserByUsername(String username) {
       return userRepository.findByUsername(username);
    }




    public UserDTO save(UserDTO userDTO){
        User user= userconverter.dtoToEntity(userDTO);
        user.setRoles(Collections.singletonList(roleRepository.findByRoleName(SecurityParams.USER)));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user=userRepository.save(user);
        return userconverter.entityToDto(user);
    }

    public List<UserDTO> saveUsers(List<UserDTO> userDTOS){
        List<User> users= userconverter.dtoToEntity(userDTOS);
        users=userRepository.saveAll(users);
        return userconverter.entityToDto(users);

    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }




    public UserDTO updateUser(Long id, UserDTO userDTO) {

        User userDB = userRepository.findById(id).get();

        if (Objects.nonNull(userDTO.getEmail()) && !"".equalsIgnoreCase(userDTO.getEmail())) {
            userDB.setEmail(userDTO.getEmail());
        }
        if (Objects.nonNull(userDTO.getPassword()) &&
                !"".equalsIgnoreCase(userDTO.getPassword())) {
            userDB.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        }
        userDB.setFirstName(userDTO.getFirstName());
        userDB.setLastName(userDTO.getLastName());
        userRepository.save(userDB);
        return userconverter.entityToDto(userDB);
    }

    public Collection<BookingDTO> GetReservations(Long id) throws NotFoundException {
       UserDTO userDTO=getById(id);
       User user= userconverter.dtoToEntity(userDTO);
        Collection<Booking> list=user.getBookings();
        return bookingConvert.entityToDto(list);
    }

    public BookingDTO bookRoom(String Name, LocalDateTime Start,LocalDateTime End){
        Booking booking=new Booking();
        booking.setRoom(roomRepository.findByName(Name));
        booking.setStartDate(Start);
        booking.setEndDate(End);
        booking.setCode(UUID.randomUUID().toString());
        return bookingConvert.entityToDto(booking);
    }


    public void addRoleToUser(String username, String rolename) {
        User appUser = userRepository.findByUsername(username);
        Role appRole = roleRepository.findByRoleName(rolename);
        appUser.getRoles().add(appRole);
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByUsername(username);
        if (user==null){
            throw new UsernameNotFoundException("Username not found in database");
        }
        Collection<GrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
    }







}
