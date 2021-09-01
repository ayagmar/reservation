package com.adservio.reservation.web;

import com.adservio.reservation.dto.BookingDTO;
import com.adservio.reservation.dto.UserDTO;
import com.adservio.reservation.exception.NotFoundException;
import com.adservio.reservation.service.BookingService;
import com.adservio.reservation.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/booking")
public class BookingRestController {
    private final BookingService service;
    @Autowired
    private EmailSenderService emailSenderService;

    @GetMapping("/all")
    public List<BookingDTO> findAllBookings() {
        return service.listAll();
    }

    @GetMapping("/{id}")
    public BookingDTO findBookingById(@PathVariable Long id) throws NotFoundException {
        return service.getById(id);
    }

    @GetMapping("/{id}/user")
    public ResponseEntity<UserDTO> GetUserByBookId(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok().body(service.GetUserByBookingId(id));
    }

    @GetMapping("/findCODE/{code}")
    public BookingDTO findBookingByCode(@PathVariable String code) {
        return service.getBookingByCode(code);
    }

    @PostMapping("/save")
    public BookingDTO addBooking(@Valid @RequestBody BookingDTO bookingDTO) throws NotFoundException {
        return service.save(bookingDTO);
    }

    @GetMapping("/all/room")
    public ResponseEntity<Collection<BookingDTO>> FetchBookingsByRoomName(@RequestBody String name) throws NotFoundException {

        return ResponseEntity.ok().body(service.GetAllByRoomName(name));
    }


    @GetMapping("/next")
    public ResponseEntity<String> getNextBooking() throws NotFoundException {
        return ResponseEntity.ok().body(service.NextBooking());
    }

    @PostMapping("/save/all")
    public List<BookingDTO> addBookings(@RequestBody List<BookingDTO> bookingDTOS) {
        return service.saveDepartments(bookingDTOS);
    }

    @PostMapping("/confirm/{id}")
    public ResponseEntity<String> confirmBooking(@RequestBody boolean confirmed, @PathVariable Long id) throws NotFoundException {
        service.confirmBooking(id, confirmed);
        if (confirmed)
            return ResponseEntity.ok().body("confirmed");
        else {
            return ResponseEntity.ok().body("not confirmed!");

        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok().body(service.deleteBooking(id));
    }


}
