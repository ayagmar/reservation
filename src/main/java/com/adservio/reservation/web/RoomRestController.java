package com.adservio.reservation.web;


import com.adservio.reservation.entities.Room;
import com.adservio.reservation.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
public class RoomRestController {

    @Autowired
    private RoomService service;
    @GetMapping("/room")
    public List<Room> findAllRooms(){
        return service.listAll();
    }
    @GetMapping("/room/{id}")
    public Room findRoomById(@PathVariable Long id) {
        return service.get(id);
    }


    @PostMapping("/room/add")
    public Room addRoom(@RequestBody Room room){
        return service.save(room);
    }
    @PostMapping("/room/addrooms")
    public List<Room> addRooms(@RequestBody List<Room> rooms){
    return service.saveRooms(rooms);
    }

    @PutMapping("/room/update")
    public Room updateRoom(@RequestBody Room room) {
        return service.updateRoom(room);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Long id) {
        return service.deleteRoom(id);
    }


}
