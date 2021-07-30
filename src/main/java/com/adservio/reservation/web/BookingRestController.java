package com.adservio.reservation.web;
import com.adservio.reservation.dto.BookingDTO;
import com.adservio.reservation.dto.UserDTO;
import com.adservio.reservation.exception.NotFoundException;
import com.adservio.reservation.service.BookingService;
import com.adservio.reservation.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/booking")
public class BookingRestController {
    private final BookingService service;
    @Autowired
    private EmailSenderService emailSenderService;

    @GetMapping("/all")
    public List<BookingDTO> findAllBookings(){
        return service.listAll();
    }

    @GetMapping("/{id}")
    public BookingDTO findBookingById(@PathVariable Long id) throws NotFoundException {
        return service.getById(id);
    }
    @GetMapping("/{id}/user")
    public ResponseEntity <UserDTO> GetUserByBookId(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok().body(service.GetUserByBookingId(id));
    }

    @GetMapping("/findCODE/{code}")
    public BookingDTO findBookingByCode(@PathVariable String code) {
        return service.getBookingByCode(code);
    }
    @PostMapping("/save")
    public BookingDTO addBooking(@RequestBody @Valid BookingDTO bookingDTO) throws NotFoundException {
        return service.save(bookingDTO);
    }

    @GetMapping("/all/room/{name}")
    public Collection<BookingDTO> FetchBookingsByRoomName(@PathVariable String name){
        return service.GetAllByRoomName(name);
    }



    @PostMapping("/save/all")
    public List<BookingDTO> addBookings(@RequestBody List<BookingDTO> bookingDTOS){
        return service.saveDepartments(bookingDTOS);
    }

    @DeleteMapping("/{id}/update")
    public String deleteBooking(@PathVariable Long id) {
        return service.deleteBooking(id);
    }

}

//    LocalDateTime startD=bookingDTO.getStartDate();
//    LocalDateTime endD=bookingDTO.getEndDate();
//    String Body="User "+bookingDTO.getUser().getFirstName()+"has reserved room "+bookingDTO.getRoom().getName().toString()+"with a duration of "
//            + ChronoUnit.HOURS.between(startD,endD)
//            +"From "+startD+" To "+endD;
//        System.out.println(Body);
//                try{
//                emailSenderService.SendEmail(bookingDTO.getUser().getEmail(),Body,"Testing!");
//                }catch (MailException mailException) {
//                System.out.println(mailException);
//                }