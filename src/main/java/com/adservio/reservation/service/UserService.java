package com.adservio.reservation.service;

import com.adservio.reservation.dao.RoleRepository;
import com.adservio.reservation.dao.UserRepository;
import com.adservio.reservation.entities.Role;
import com.adservio.reservation.entities.User;
import com.adservio.reservation.entities.dto.UserDTO;
import com.adservio.reservation.exception.NotFoundException;
import com.adservio.reservation.mapper.UserConvert;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserConvert converter;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, UserConvert converter) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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
        user.getRoles().add(roleRepository.findByRoleName("USER"));
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




    public UserDTO updateUser(Long id, UserDTO userDTO) {

        User userDB = userRepository.findById(id).get();

        if (Objects.nonNull(userDTO.getEmail()) && !"".equalsIgnoreCase(userDTO.getEmail())) {
            userDB.setEmail(userDB.getEmail());
        }
        if (Objects.nonNull(userDTO.getPassword()) &&
                !"".equalsIgnoreCase(userDTO.getPassword())) {
            userDB.setPassword(userDB.getPassword());
        }
        userDB.setFirstName(userDTO.getFirstName());
        userDB.setLastName(userDTO.getLastName());
        userRepository.save(userDB);


        return converter.entityToDto(userDB);
    }



}
