package com.adservio.reservation.service;

import com.adservio.reservation.dao.UserRepository;
import com.adservio.reservation.entities.User;
import com.adservio.reservation.entities.dto.UserDTO;
import com.adservio.reservation.exception.NotFoundException;
import com.adservio.reservation.mapper.UserConvert;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserConvert converter;

    public UserService(UserRepository userRepository, UserConvert converter) {
        this.userRepository = userRepository;
        this.converter = converter;
    }

    public List<UserDTO> listAll(){

        return converter.entityToDto(userRepository.findAll());
    }

    public UserDTO getById(Long id) throws NotFoundException{

        Optional<User> user=userRepository.findById(id);
        if(user.isEmpty()) throw new NotFoundException("User Not Available");
        return converter.entityToDto(user.get());
    }

    public UserDTO GetUserByEmail(String email) {
        return converter.entityToDto(userRepository.findByEmail(email));
    }

    public UserDTO save(UserDTO userDTO){
        User user=converter.dtoToEntity(userDTO);
        user=userRepository.save(user);
        return converter.entityToDto(user);
    }

    public List<UserDTO> saveUsers(List<UserDTO> userDTOS){
        List<User> users=converter.dtoToEntity(userDTOS);
        users=userRepository.saveAll(users);
        return converter.entityToDto(users);

    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }




}
