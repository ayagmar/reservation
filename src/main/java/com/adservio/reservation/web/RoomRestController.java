package com.adservio.reservation.web;

import com.adservio.reservation.dto.BookingDTO;
import com.adservio.reservation.dto.RoomDTO;
import com.adservio.reservation.dto.UserDTO;
import com.adservio.reservation.exception.NotFoundException;
import com.adservio.reservation.service.RoomService;
import com.adservio.reservation.utilClass.FormClass;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/room")

public class RoomRestController {

    private final RoomService service;


    @GetMapping("/all")
    public ResponseEntity<List<RoomDTO>> findAllRooms() {
        return ResponseEntity.ok().body(service.listAll());
    }

    @GetMapping("/available")
    public ResponseEntity<List<RoomDTO>> findAvailable() {
        return ResponseEntity.ok().body(service.listAvailable());
    }

//    @GetMapping("/available")
//    public ResponseEntity<List<RoomDTO>> findAvailableRooms() {
//        return ResponseEntity.ok().body(service.listAvailable());
//    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> findRoomById(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok().body(service.getById(id));
    }


    @GetMapping("/findname")
    public ResponseEntity<RoomDTO> findRoomByName(@RequestParam("name") String name) throws NotFoundException {
        return ResponseEntity.ok().body(service.getRoomByName(name));
    }

    @GetMapping("/availability")
    public ResponseEntity<List<RoomDTO>> ListAvailable(@RequestParam("start") String start, @RequestParam("end") String end) {
        return ResponseEntity.ok().body(service.FindAvailable(start, end));
    }

    @GetMapping("/{id}/bookings")
    public ResponseEntity<Collection<BookingDTO>> GetBookingsByRoom(@PathVariable("id") Long id) throws NotFoundException {
        return ResponseEntity.ok().body(service.listBookByRoom(id));
    }

    @GetMapping("/booking/latest")
    public ResponseEntity<RoomDTO> FetchLatestReservedRoom() throws NotFoundException {
        return ResponseEntity.ok().body(service.GetLastReservedRoom());
    }

    @GetMapping("/mostbooked")
    public ResponseEntity<RoomDTO> getMostBookedRoom() throws NotFoundException {
        return ResponseEntity.ok().body(service.mostBookedRoom());
    }

    @GetMapping("/{id}/users")
    public ResponseEntity<Collection<UserDTO>> GetUsersByRoom(@PathVariable("id") Long id) throws NotFoundException {
        return ResponseEntity.ok().body(service.GetUsersByRoom(id));
    }


    @PostMapping("/save")
    public ResponseEntity<RoomDTO> addRoom(@RequestBody FormClass.RoomForm roomdto) throws URISyntaxException {
        RoomDTO result = service.save(roomdto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/room/save").toUriString());
        return ResponseEntity.created(uri).body(result);
    }

    @PostMapping("/save/all")
    public ResponseEntity<List<RoomDTO>> addRooms(@RequestBody List<RoomDTO> rooms) throws URISyntaxException {

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/room/save/all").toUriString());
        return ResponseEntity.created(uri).body(service.saveRooms(rooms));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable("id") Long id,
                                              @RequestBody RoomDTO room) {
        RoomDTO result = service.updateRoom(id, room);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/room/update/{id}").toUriString());
        return ResponseEntity.created(uri).body(result);
    }


    @DeleteMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Long id) {
        service.deleteRoom(id);
        return "Deleted successfully";
    }


}
