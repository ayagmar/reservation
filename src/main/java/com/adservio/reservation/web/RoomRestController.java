package com.adservio.reservation.web;
import com.adservio.reservation.entities.dto.RoomDTO;
import com.adservio.reservation.exception.NotFoundException;
import com.adservio.reservation.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/room")
public class RoomRestController {

    private final RoomService service;


    @GetMapping("/all")
    public ResponseEntity<List<RoomDTO>> findAllRooms(){
        return ResponseEntity.ok().body(service.listAll());
    }

    @GetMapping("/findID/{id}")
    public  ResponseEntity<RoomDTO> findRoomById(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok().body(service.getById(id));
    }

    @GetMapping("/findNAME/{name}")
    public ResponseEntity<RoomDTO> findRoomByName(@PathVariable String name) throws NotFoundException {
        return ResponseEntity.ok().body(service.getRoomByName(name));
    }

    @PostMapping("/save")
    public ResponseEntity<RoomDTO> addRoom(@RequestBody RoomDTO roomdto) throws URISyntaxException {
        RoomDTO result =service.save(roomdto);
        URI uri= URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/room/save").toUriString());
        return  ResponseEntity.created(uri).body(result);
    }

    @PostMapping("/save/all")
    public ResponseEntity<List<RoomDTO>>  addRooms(@RequestBody List<RoomDTO> rooms)  throws URISyntaxException{

        URI uri= URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/room/save/all").toUriString());
        return  ResponseEntity.created(uri).body(service.saveRooms(rooms));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable("id") Long id,
                                          @RequestBody RoomDTO room) {
        RoomDTO result = service.updateRoom(id,room);
        URI uri= URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/room/update/{id}").toUriString());
        return  ResponseEntity.created(uri).body(result);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Long id) {
        service.deleteRoom(id);
        return "Deleted successfully";
    }


}
