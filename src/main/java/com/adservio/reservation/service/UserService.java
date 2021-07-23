package com.adservio.reservation.service;

import com.adservio.reservation.dao.RoleRepository;
import com.adservio.reservation.dao.UserRepository;
import com.adservio.reservation.entities.Role;
import com.adservio.reservation.entities.User;
import com.adservio.reservation.entities.dto.UserDTO;
import com.adservio.reservation.exception.NotFoundException;
import com.adservio.reservation.mapper.UserConvert;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserConvert converter;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


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

    public User GetUserByUsername(String username) {
       return userRepository.findByEmail(username);
    }



    public UserDTO save(UserDTO userDTO){
        User user=converter.dtoToEntity(userDTO);
        user.getRoles().add(roleRepository.findByRoleName("USER"));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
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
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
    }







}
