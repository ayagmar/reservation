package com.adservio.reservation.web;


import com.adservio.reservation.entities.Room;
import com.adservio.reservation.entities.dto.RoomDTO;
import com.adservio.reservation.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RoomRestController {

    @Autowired
    private RoomService service;
    @GetMapping("/rooms")
    public List<RoomDTO> findAllRooms(){
        return service.listAll();
    }
    @GetMapping("/room/{id}")
    public RoomDTO findRoomById(@PathVariable Long id) {
        return service.getById(id);
    }
    @GetMapping("/room/{name}")
    public RoomDTO findDepartmentByName(@PathVariable String name) {
        return service.getRoomByName(name);
    }
    @PostMapping("/room/add")
    public RoomDTO addRoom(@RequestBody RoomDTO roomdto){
        return service.save(roomdto);
    }
    @PostMapping("/room/addrooms")
    public List<RoomDTO> addRooms(@RequestBody List<RoomDTO> rooms){
    return service.saveRooms(rooms);
    }
    @PutMapping("/room/update")
    public Room updateRoom(@RequestBody Room room) {
        return service.updateRoom(room);
    }
    @DeleteMapping("/room/delete/{id}")
    public String deleteRoom(@PathVariable Long id) {
        return service.deleteRoom(id);
    }


}
