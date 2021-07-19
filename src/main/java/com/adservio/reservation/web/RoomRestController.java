package com.adservio.reservation.web;
import com.adservio.reservation.entities.dto.RoomDTO;
import com.adservio.reservation.exception.NotFoundException;
import com.adservio.reservation.service.RoomService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/room")
public class RoomRestController {

    private final RoomService service;

    public RoomRestController(RoomService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<RoomDTO> findAllRooms(){
        return service.listAll();
    }
    @GetMapping("/findID/{id}")
    public RoomDTO findRoomById(@PathVariable Long id) throws NotFoundException {
        return service.getById(id);
    }
    @GetMapping("/findNAME/{name}")
    public RoomDTO findRoomByName(@PathVariable String name) {
        return service.getRoomByName(name);
    }
    @PostMapping("/save")
    public RoomDTO addRoom(@RequestBody RoomDTO roomdto){
        return service.save(roomdto);
    }
    @PostMapping("/save/all")
    public List<RoomDTO> addRooms(@RequestBody List<RoomDTO> rooms){
    return service.saveRooms(rooms);
    }
    @PutMapping("/update/{id}")
    public RoomDTO updateRoom(@PathVariable("id") Long id,
                                          @RequestBody RoomDTO room) {
        return service.updateRoom(id,room);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Long id) {
        service.deleteRoom(id);
        return "Deleted successfully";
    }


}
