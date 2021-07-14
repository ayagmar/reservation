package com.adservio.reservation.web;

import com.adservio.reservation.entities.dto.BookingDTO;
import com.adservio.reservation.entities.dto.DepartmentDTO;
import com.adservio.reservation.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")

public class BookingRestController {
    @Autowired
    BookingService service;
    @GetMapping("/bookings")
    public List<BookingDTO> findAllRooms(){
        return service.listAll();
    }

    @GetMapping("/booking/{id}")
    public BookingDTO findDepartmentById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/booking/{code}")
    public BookingDTO findBookingByEmail(@PathVariable String code) {
        return service.getBookingByCode(code);
    }
    @PostMapping("/booking/add")
    public BookingDTO addBooking(@RequestBody BookingDTO bookingDTO){
        return service.save(bookingDTO);
    }

    @PostMapping("/booking/addall")
    public List<BookingDTO> addBookings(@RequestBody List<BookingDTO> bookingDTOS){
        return service.saveDepartments(bookingDTOS);
    }

    @DeleteMapping("/booking/delete/{id}")
    public String deleteBooking(@PathVariable Long id) {
        return service.deleteBooking(id);
    }

}
