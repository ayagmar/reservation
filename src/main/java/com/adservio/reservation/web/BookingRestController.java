package com.adservio.reservation.web;
import com.adservio.reservation.entities.dto.BookingDTO;
import com.adservio.reservation.service.BookingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking")

public class BookingRestController {
    final
    BookingService service;

    public BookingRestController(BookingService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<BookingDTO> findAllBookings(){
        return service.listAll();
    }

    @GetMapping("/findID/{id}")
    public BookingDTO findBookingById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/findCODE/{code}")
    public BookingDTO findBookingByEmail(@PathVariable String code) {
        return service.getBookingByCode(code);
    }
    @PostMapping("/add")
    public BookingDTO addBooking(@RequestBody BookingDTO bookingDTO){
        return service.save(bookingDTO);
    }

    @PostMapping("/addall")
    public List<BookingDTO> addBookings(@RequestBody List<BookingDTO> bookingDTOS){
        return service.saveDepartments(bookingDTOS);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBooking(@PathVariable Long id) {
        return service.deleteBooking(id);
    }

}
