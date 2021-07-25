package com.adservio.reservation.web;
import com.adservio.reservation.entities.dto.BookingDTO;
import com.adservio.reservation.exception.NotFoundException;
import com.adservio.reservation.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/booking")

public class BookingRestController {
    private final
    BookingService service;


    @GetMapping("/all")
    public List<BookingDTO> findAllBookings(){
        return service.listAll();
    }

    @GetMapping("/findID/{id}")
    public BookingDTO findBookingById(@PathVariable Long id) throws NotFoundException {
        return service.getById(id);
    }

    @GetMapping("/findCODE/{code}")
    public BookingDTO findBookingByCode(@PathVariable String code) {
        return service.getBookingByCode(code);
    }
    @PostMapping("/save")
    public BookingDTO addBooking(@RequestBody BookingDTO bookingDTO){
        return service.save(bookingDTO);
    }

    @PostMapping("/save/all")
    public List<BookingDTO> addBookings(@RequestBody List<BookingDTO> bookingDTOS){
        return service.saveDepartments(bookingDTOS);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBooking(@PathVariable Long id) {
        return service.deleteBooking(id);
    }

}
