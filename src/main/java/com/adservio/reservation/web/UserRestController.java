package com.adservio.reservation.web;


import com.adservio.reservation.dto.BookingDTO;
import com.adservio.reservation.entities.Booking;
import com.adservio.reservation.entities.Role;
import com.adservio.reservation.entities.User;
import com.adservio.reservation.dto.UserDTO;
import com.adservio.reservation.exception.NotFoundException;
import com.adservio.reservation.security.SecurityParams;
import com.adservio.reservation.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserRestController {
    private final UserService service;

    @GetMapping("/all")
    public List<UserDTO> findAllUsers(){
        return service.listAll();
    }
    @GetMapping("/findID/{id}")
    public UserDTO findUserById(@PathVariable Long id) throws NotFoundException {
        return service.getById(id);
    }
    @GetMapping("/findEMAIL/{email}")
    public UserDTO findUserByEmail(@PathVariable String email) {
        return service.GetUserByEmail(email);
    }
    @PostMapping("/save")
    public UserDTO addUser(@Valid @RequestBody UserDTO userDTO){
        return service.save(userDTO);
    }
    @PostMapping("/save/all")
    public List<UserDTO> addUsers(@RequestBody List<UserDTO> userDTOS){
        return service.saveUsers(userDTOS);
    }

    @PutMapping("/update/{id}")
    public UserDTO updateUser(@PathVariable("id") Long id,
                              @RequestBody UserDTO user) {
        return service.updateUser(id,user);
    }
    @GetMapping("/reservations/{id}")
    public Collection<BookingDTO> GetReservations(@PathVariable Long id) throws NotFoundException {
        return service.GetReservations(id);
    }



    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
         service.deleteUser(id);
         return "Delete successfully";
    }
    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(SecurityParams.JWT_HEADER_NAME);
        if(authorizationHeader != null && authorizationHeader.startsWith(SecurityParams.JWT_HEADER_PREFIX)) {
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
            }catch (Exception exception) {
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

