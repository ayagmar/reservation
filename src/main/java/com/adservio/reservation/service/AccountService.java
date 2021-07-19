package com.adservio.reservation.service;

import com.adservio.reservation.dao.RoleRepository;
import com.adservio.reservation.dao.UserRepository;
import com.adservio.reservation.entities.Role;
import com.adservio.reservation.entities.User;
import com.adservio.reservation.entities.dto.UserDTO;
import com.adservio.reservation.mapper.UserConvert;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountService {
private final UserRepository userRepository;
private final BCryptPasswordEncoder bCryptPasswordEncoder;
private final RoleRepository roleRepository;
private final UserConvert converter;
    public AccountService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository, UserConvert converter) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
        this.converter = converter;
    }

    public UserDTO RegisterUser(String Email,String password,String confirmedPassword){
        User user=userRepository.findByEmail(Email);
        if (user != null) throw new RuntimeException("User already exists");
        if (!password.equals(confirmedPassword)) throw new RuntimeException("Please confirm your password !");
        User appUser = new User();
        appUser.setEmail(Email);
        appUser.setPassword(bCryptPasswordEncoder.encode(password));
        appUser.setActive(true);
        userRepository.save(appUser);
        addRoleToUser(Email);
        return converter.entityToDto(appUser);
    }

    private void addRoleToUser(String email) {
        User appUser = userRepository.findByEmail(email);
        Role appRole = roleRepository.findByRoleName("USER");
        appUser.getRoles().add(appRole);
    }

    public User loadUserByEmail(String Email) {
        return userRepository.findByEmail(Email);
    }

}
