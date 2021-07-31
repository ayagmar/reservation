package com.adservio.reservation.web;

import com.adservio.reservation.dto.RoleDTO;
import com.adservio.reservation.exception.NotFoundException;
import com.adservio.reservation.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/Role")
public class RoleRestController {
    private final RoleService service;

    @GetMapping("/all")
    public List<RoleDTO> findAllRooms() {
        return service.listAll();
    }

    @GetMapping("/findID/{id}")
    public RoleDTO findRoomById(@PathVariable Long id) throws NotFoundException {
        return service.getById(id);
    }

    @GetMapping("/findNAME/{name}")
    public RoleDTO findRoomByName(@PathVariable String name) {
        return service.getRoleByName(name);
    }

    @PostMapping("/save")
    public RoleDTO addRoom(@RequestBody RoleDTO roleDTO) {
        return service.save(roleDTO);
    }

    @PostMapping("/save/all")
    public List<RoleDTO> addRooms(@RequestBody List<RoleDTO> roles) {
        return service.saveRoles(roles);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Long id) {
        service.deleteRole(id);
        return "Deleted successfully";
    }

}
