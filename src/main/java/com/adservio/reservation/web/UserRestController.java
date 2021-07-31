package com.adservio.reservation.web;

import com.adservio.reservation.dao.BookingRepository;
import com.adservio.reservation.dto.BookingDTO;
import com.adservio.reservation.dto.UserDTO;
import com.adservio.reservation.entities.Booking;
import com.adservio.reservation.entities.Role;
import com.adservio.reservation.entities.User;
import com.adservio.reservation.exception.NotFoundException;
import com.adservio.reservation.mapper.UserConvert;
import com.adservio.reservation.security.SecurityParams;
import com.adservio.reservation.service.BookingService;
import com.adservio.reservation.service.EmailSenderService;
import com.adservio.reservation.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private final UserService service;
    private final EmailSenderService emailSenderService;

    private final BookingService bookingService;
    private final UserConvert userConvert;

    private final BookingRepository bookingRepository;

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> findAllUsers() {
        return ResponseEntity.ok().body(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok().body(service.getById(id));
    }

    @GetMapping("/reservations")
    public ResponseEntity<Collection<BookingDTO>> GetReservations() throws NotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User LoggedInUser = service.GetUserByUsername(auth.getPrincipal().toString());
        return ResponseEntity.ok().body(service.GetReservations(LoggedInUser.getId()));
    }

    @PostMapping("/book/{roomName}")
    public ResponseEntity<String> BookARoom(@PathVariable String roomName,
                                            @RequestParam String dateStart,
                                            @RequestParam String dateEnd, @RequestBody String description) throws NotFoundException {
        LocalDateTime dateS = LocalDateTime.parse(dateStart);
        LocalDateTime dateE = LocalDateTime.parse(dateEnd);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User LoggedInUser = service.GetUserByUsername(auth.getPrincipal().toString());
        UserDTO userDTO = userConvert.entityToDto(LoggedInUser);
        BookingDTO booking = service.bookRoom(roomName, dateS, dateE);
        booking.setUser(userDTO);
        booking.setDescription(description);
        if (dateS.isAfter(dateE) || dateE.isEqual(dateS) || dateE.isBefore(LocalDateTime.now()) || dateS.isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Your date does not fit criteria!");
        }
        List<User> users = service.FetchUsersByRole(SecurityParams.ADMIN);
        String Body = "User " + booking.getUser().getFirstName() + " has reserved room " + booking.getRoom().getName() + " with a duration of "
                + ChronoUnit.HOURS.between(dateS, dateE) + " Hours"
                + " From " + dateS + " To " + dateE;
        for (User user : users) {
            try {
                emailSenderService.SendEmail(user.getEmail(), Body, "Testing!");
            } catch (MailException mailException) {
                mailException.printStackTrace();
            }
        }
        bookingService.save(booking);
        return ResponseEntity.ok().body("Added successfully");
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

    @DeleteMapping("/booking/cancel")
    public ResponseEntity<String> Cancelbooking(@RequestParam String Code) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User LoggedInUser = service.GetUserByUsername(auth.getPrincipal().toString());

        Collection<Booking> bookings = LoggedInUser.getBookings();
        Booking booking = bookingRepository.findByCode(Code);
        LocalDateTime now = LocalDateTime.now();

        if (Objects.isNull(booking)) {
            return ResponseEntity.status(404).body("Please enter your correct reservation code!");
        }

        if (booking.getEndDate().isBefore(now)) {
            return ResponseEntity.status(405).body("You cannot cancel a booking that has already ended!");
        }

        if (bookings.contains(booking)) {
            System.out.println(bookings.contains(booking));
            bookingService.deleteBooking(booking.getId());

            return ResponseEntity.ok().body("DELETED SUCCESSFULLY");
        } else
            System.out.println("ERROR DELETING RESERVATION");
        return ResponseEntity.status(405).body("Error deleting reservation that is not yours");

    }


    @PutMapping("/{id}/update")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") Long id,
                                              @RequestBody UserDTO user) {
        UserDTO result = service.updateUser(id, user);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/room/{id}/update").toUriString());
        return ResponseEntity.created(uri).body(result);
    }

    @RequestMapping("/{id}/send")
    public String sendmail(@PathVariable("id") Long id) throws NotFoundException {
        UserDTO user = service.getById(id);
        try {
            emailSenderService.SendEmail(user.getEmail(), "teestt", "Booking Reservation ");
        } catch (MailException mailException) {
            mailException.printStackTrace();
        }
        return "Congratulations! Your mail has been send to the user.";
    }

    @RequestMapping("/admin/booking/confirm/{confirmed}")
    public boolean ConfirmedReservation(@PathVariable boolean confirmed) {
        return confirmed;
    }


    @DeleteMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return "Delete successfully";
    }


    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(SecurityParams.JWT_HEADER_NAME);
        if (authorizationHeader != null && authorizationHeader.startsWith(SecurityParams.JWT_HEADER_PREFIX)) {
            try {
                String refresh_token = authorizationHeader.substring(SecurityParams.JWT_HEADER_PREFIX.length());
                Algorithm algorithm = Algorithm.HMAC256(SecurityParams.PRIVATE_SECRET.getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = service.GetUserByUsername(username);
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + SecurityParams.JWT_EXPIRATION))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}

