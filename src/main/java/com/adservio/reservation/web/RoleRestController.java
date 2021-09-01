package com.adservio.reservation.web;

import com.adservio.reservation.dto.RoleDTO;
import com.adservio.reservation.exception.NotFoundException;
import com.adservio.reservation.service.RoleService;
import com.adservio.reservation.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/role")

public class RoleRestController {
    private final RoleService service;
    private final UserService userService;

    @GetMapping("/all")
    public List<RoleDTO> findAllRoles() {
        return service.listAll();
    }

    @GetMapping("/findID/{id}")
    public RoleDTO findRoleById(@PathVariable Long id) throws NotFoundException {
        return service.getById(id);
    }

    @GetMapping("/findNAME/{name}")
    public RoleDTO findRoleByName(@PathVariable String name) {
        return service.getRoleByName(name);
    }

    @PostMapping("/save")
    public RoleDTO addRole(@RequestBody RoleDTO roleDTO) {
        return service.save(roleDTO);
    }

    @PostMapping("/save/all")
    public List<RoleDTO> addRoles(@RequestBody List<RoleDTO> roles) {
        return service.saveRoles(roles);
    }

    @DeleteMapping("/delete/{id}")
    public String deleterole(@PathVariable Long id) {
        service.deleteRole(id);
        return "Deleted successfully";
    }

    @PostMapping("/addToUser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @Data
    public static class RoleToUserForm {
        private String username;
        private String roleName;
    }

}
