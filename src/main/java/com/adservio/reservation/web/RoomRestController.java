package com.adservio.reservation.web;
import com.adservio.reservation.dto.BookingDTO;
import com.adservio.reservation.dto.RoomDTO;
import com.adservio.reservation.entities.Room;
import com.adservio.reservation.exception.NotFoundException;
import com.adservio.reservation.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Collection;
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

    @GetMapping("/{id}")
    public  ResponseEntity<RoomDTO> findRoomById(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok().body(service.getById(id));
    }

    @GetMapping("/findname")
    public ResponseEntity<RoomDTO> findRoomByName(@RequestParam("name") String name) throws NotFoundException {
        return ResponseEntity.ok().body(service.getRoomByName(name));
    }

//    @GetMapping("/available/{dates}/{datee}")
//    public ResponseEntity<List<RoomDTO>> CheckAvailability(@PathVariable String dates,@PathVariable String datee) {
//        return ResponseEntity.ok().body(service.FindAvailable(dates,datee));
//    }
@GetMapping("/available")
public ResponseEntity<List<RoomDTO>> ListAvailable(@RequestParam("dateStart")
                                                 @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
                                                 LocalDateTime dateS,
                                                 @RequestParam("dateEnd")
                                                 @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
                                                    LocalDateTime dateE){
        return ResponseEntity.ok().body(service.FindAvailable(dateS, dateE));
}

    @GetMapping("/{id}/bookings")
    public ResponseEntity<Collection<BookingDTO>> updateRoom(@PathVariable("id") Long id) throws NotFoundException {
        return ResponseEntity.ok().body(service.listBookByRoom(id));
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

    @PutMapping("/{id}/update")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable("id") Long id,
                                          @RequestBody RoomDTO room) {
        RoomDTO result = service.updateRoom(id,room);
        URI uri= URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/room/{id}/update").toUriString());
        return  ResponseEntity.created(uri).body(result);
    }

    @DeleteMapping("/{id}/delete")
    public String deleteRoom(@PathVariable Long id) {
        service.deleteRoom(id);
        return "Deleted successfully";
    }


}
