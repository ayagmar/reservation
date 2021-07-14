package com.adservio.reservation.web;


import com.adservio.reservation.entities.dto.UserDTO;
import com.adservio.reservation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestController {
    @Autowired
    private UserService service;
    @GetMapping("/users")
    public List<UserDTO> findAllUsers(){
        return service.listAll();
    }
    @GetMapping("/user/{id}")
    public UserDTO findRoomById(@PathVariable Long id) {
        return service.getById(id);
    }
    @GetMapping("/user/{email}")
    public UserDTO findUserByEmail(@PathVariable String email) {
        return service.GetUserByEmail(email);
    }
    @PostMapping("/user/add")
    public UserDTO addUser(@RequestBody UserDTO userDTO){
        return service.save(userDTO);
    }
    @PostMapping("/user/addusers")
    public List<UserDTO> addRooms(@RequestBody List<UserDTO> userDTOS){
        return service.saveUsers(userDTOS);
    }
    /*
    @PutMapping("/room/update")
    public User updateRoom(@RequestBody User user) {
        return service.updateUser(user);
    }
    */
    @DeleteMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        return service.deleteUser(id);
    }
}
