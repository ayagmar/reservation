package com.adservio.reservation.web;

import com.adservio.reservation.dto.BookingDTO;
import com.adservio.reservation.dto.UserDTO;
import com.adservio.reservation.entities.User;
import com.adservio.reservation.exception.NotFoundException;
import com.adservio.reservation.service.UserService;
import com.adservio.reservation.utilClass.FormClass;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private final UserService service;

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> findAllUsers() {
        return ResponseEntity.ok().body(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok().body(service.getById(id));
    }

    @GetMapping("/bookings")
    public ResponseEntity<Collection<BookingDTO>> GetReservations() throws NotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User LoggedInUser = service.GetUserByUsername(auth.getPrincipal().toString());
        return ResponseEntity.ok().body(service.GetReservations(LoggedInUser.getId()));
    }


    @PostMapping("/book/")
    public ResponseEntity<String> BookARoom(@RequestBody FormClass.UserBookingForm form) throws NotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User LoggedInUser = service.GetUserByUsername(auth.getPrincipal().toString());
        return service.bookRoom(form, LoggedInUser.getId());
    }


    @GetMapping("/findEMAIL/{email}")
    public ResponseEntity<UserDTO> findUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok().body(service.GetUserByEmail(email));
    }

    @PostMapping("/save")
    public ResponseEntity<UserDTO> addUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO result = service.save(userDTO);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(result);
    }

    @PostMapping("/save/all")
    public ResponseEntity<List<UserDTO>> addUsers(@RequestBody List<UserDTO> userDTOS) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save/all").toUriString());
        return ResponseEntity.created(uri).body(service.saveUsers(userDTOS));
    }

    @DeleteMapping("/booking/cancel/{Code}")
    public ResponseEntity<String> cancelBooking(@PathVariable String Code) throws NotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User LoggedInUser = service.GetUserByUsername(auth.getPrincipal().toString());
        return service.cancelBooking(LoggedInUser.getId(), Code);

    }


    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") Long id,
                                              @RequestBody UserDTO user) {
        UserDTO result = service.updateUser(id, user);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/update/{id}").toUriString());
        return ResponseEntity.created(uri).body(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) throws NotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User LoggedInUser = service.GetUserByUsername(auth.getPrincipal().toString());
        if (LoggedInUser.getId().equals(service.getById(id).getId())) {
            return ResponseEntity.status(405).body("cannot delete current logged in user");
        } else {
            service.deleteUser(id);
            return ResponseEntity.ok().body("User deleted !");
        }
    }


//    @GetMapping("/token/refresh")
//    public void TokenRefresh(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        service.refreshToken(request, response);
//    }


}

