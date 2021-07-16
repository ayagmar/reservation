package com.adservio.reservation.web;


import com.adservio.reservation.entities.dto.UserDTO;
import com.adservio.reservation.exception.NotFoundException;
import com.adservio.reservation.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserRestController {
    private final UserService service;

    public UserRestController(UserService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<UserDTO> findAllUsers(){
        return service.listAll();
    }
    @GetMapping("/findID/{id}")
    public UserDTO findUserById(@PathVariable Long id) throws NotFoundException {
        return service.getById(id);
    }
    @GetMapping("/findEMAIL/{email}")
    public UserDTO findUserByEmail(@PathVariable String email) {
        return service.GetUserByEmail(email);
    }
    @PostMapping("/add")
    public UserDTO addUser(@Valid @RequestBody UserDTO userDTO){
        return service.save(userDTO);
    }
    @PostMapping("/addull")
    public List<UserDTO> addUsers(@RequestBody List<UserDTO> userDTOS){
        return service.saveUsers(userDTOS);
    }

    @PutMapping("/update/{id}")
    public UserDTO updateUser(@PathVariable("id") Long id,
                              @RequestBody UserDTO user) {
        return service.updateUser(id,user);
    }
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
         service.deleteUser(id);
         return "Delete successfully";
    }
}
