package com.adservio.reservation.service;

import com.adservio.reservation.dao.BookingRepository;
import com.adservio.reservation.dao.RoleRepository;
import com.adservio.reservation.dao.UserRepository;
import com.adservio.reservation.dto.BookingDTO;
import com.adservio.reservation.dto.UserDTO;
import com.adservio.reservation.entities.Booking;
import com.adservio.reservation.entities.Role;
import com.adservio.reservation.entities.User;
import com.adservio.reservation.exception.NotFoundException;
import com.adservio.reservation.mapper.BookingConvert;
import com.adservio.reservation.mapper.UserConvert;
import com.adservio.reservation.security.SecurityParams;
import com.adservio.reservation.utilClass.FormClass;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final RoleRepository roleRepository;
    private final BookingService bookingService;
    private final UserConvert userconverter;
    private final BookingConvert bookingConvert;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailSenderService emailSenderService;


    public List<UserDTO> listAll() {
        return userconverter.entityToDto(userRepository.findAll());
    }

    public UserDTO getById(Long id) throws NotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) throw new NotFoundException("User Not Available");
        return userconverter.entityToDto(user.get());
    }

    public UserDTO GetUserByEmail(String email) {
        return userconverter.entityToDto(userRepository.findByEmail(email));
    }

    public User GetUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public ResponseEntity<?> Signup(UserDTO signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(" Email is already in use!");
        }

        User user = userconverter.dtoToEntity(signUpRequest);
        user.setActive(true);
        user.setRoles(Collections.singletonList(roleRepository.findByRoleName(SecurityParams.USER)));
        user.setPassword(bCryptPasswordEncoder.encode(signUpRequest.getPassword()));
        user = userRepository.save(user);
        return ResponseEntity.ok().body(userconverter.entityToDto(user));
    }

    public UserDTO save(UserDTO userDTO) {
        User user = userconverter.dtoToEntity(userDTO);
        user.setRoles(Collections.singletonList(roleRepository.findByRoleName(SecurityParams.USER)));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        user.setActive(true);
        return userconverter.entityToDto(user);
    }

    public ResponseEntity<String> bookRoom(FormClass.UserBookingForm form, Long id) throws NotFoundException {
        LocalDateTime dateS = LocalDateTime.parse(form.getDateStart());
        LocalDateTime dateE = LocalDateTime.parse(form.getDateEnd());
        UserDTO userDTO = getById(id);
        BookingDTO booking = bookingService.bookRoom(form.getRoomName(), dateS, dateE);
        booking.setUser(userDTO);
        booking.setDescription(form.getDescription());
        if (dateS.isAfter(dateE) ||
                dateE.isEqual(dateS) ||
                dateE.isBefore(LocalDateTime.now()) ||
                dateS.isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Your date does not fit criteria!");
        }
        BookingDTO bookingDTO = bookingService.save(booking);

        if (!Objects.isNull(bookingDTO)) {

            Period period = Period.between(dateS.toLocalDate(), dateE.toLocalDate());
            period = period.minusDays(dateE.toLocalTime().compareTo(dateS.toLocalTime()) >= 0 ? 0 : 1);
            Duration duration = Duration.between(dateS, dateE);
            duration = duration.minusDays(duration.toDaysPart());
            int hours = duration.toHoursPart();
            String minutes = (duration.toMinutesPart() > 0 ? (duration.toMinutesPart() + " minutes") : "");
            String days = period.getDays() > 1 ? " days" : " day";
            String isday = (period.getDays() > 0 ? (period.getDays() + days) : "");
            List<User> users = FetchUsersByRole(SecurityParams.ADMIN);
            String Body = "Reservation " + booking.getCode() + ": User " + booking.getUser().getFirstName() + " has reserved room "
                    + booking.getRoom().getName() + " with a duration of "
                    + isday
                    + hours + " Hour" + (hours > 1 ? "s " : " ")
                    + minutes
                    + "\n\nReservation starts at  " + dateS.format(DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm")) +
                    " Please confirm  booking ID : " + bookingDTO.getId() +
                    " with a  POST request at  http://localhost:8080/" +
                    bookingDTO.getId() + "/confirm?confirmed=true";
            for (User user : users) {
                try {
                    emailSenderService.SendEmail(user.getEmail(), Body, "Confirm Booking!");
                } catch (MailException mailException) {
                    mailException.printStackTrace();
                }
            }
        }
        return ResponseEntity.ok().body("Added successfully");
    }


    public ResponseEntity<String> cancelBooking(Long id, String Code) throws NotFoundException {
        User user = userRepository.getById(id);
        Collection<Booking> bookings = user.getBookings();
        Booking booking = bookingRepository.findByCode(Code);
        LocalDateTime now = LocalDateTime.now();
        if (Objects.isNull(booking)) {
            return ResponseEntity.status(404).body("Please enter your correct reservation code!");
        }

        if (booking.getEndDate().isBefore(now)) {
            return ResponseEntity.status(405).body("You cannot cancel a booking that has already ended!");
        }

        if (bookings.contains(booking)) {

            booking.getRoom().setReserved(false);
            user.getBookings().remove(booking);
            String s = bookingService.deleteBooking(booking.getId());
            userRepository.save(user);
            List<User> users = FetchUsersByRole(SecurityParams.ADMIN);
            String Body = "User " + user.getUsername() + " has cancelled the reservation "
                    + booking.getCode() +
                    " at " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm")) + " regarding room : " + booking.getRoom().getName();

            for (User user1 : users) {
                try {
                    emailSenderService.SendEmail(user1.getEmail(), Body, "Booking cancelled!");
                } catch (MailException mailException) {
                    mailException.printStackTrace();
                }
            }

            return ResponseEntity.ok().body(s);
        } else {
            return ResponseEntity.status(405).body("Error deleting reservation that is not yours");
        }

    }


    public List<UserDTO> saveUsers(List<UserDTO> userDTOS) {
        List<User> users = userconverter.dtoToEntity(userDTOS);
        users = userRepository.saveAll(users);
        return userconverter.entityToDto(users);

    }

    public void deleteUser(Long id) {
        User user = userRepository.getById(id);
        userRepository.delete(user);
    }


    public UserDTO updateUser(Long id, UserDTO userDTO) {

        User userDB = userRepository.findById(id).get();

        if (Objects.nonNull(userDTO.getEmail()) && !"".equalsIgnoreCase(userDTO.getEmail())) {
            userDB.setEmail(userDTO.getEmail());
        }
        if (Objects.nonNull(userDTO.getPassword()) &&
                !"".equalsIgnoreCase(userDTO.getPassword())) {
            userDB.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        }
        if (Objects.nonNull(userDTO.getUsername()) &&
                !"".equalsIgnoreCase(userDTO.getUsername())) {
            userDB.setUsername(userDTO.getUsername());
        }

        userDB.setFirstName(userDTO.getFirstName());
        userDB.setLastName(userDTO.getLastName());
        userRepository.save(userDB);
        return userconverter.entityToDto(userDB);
    }

    public Collection<BookingDTO> GetReservations(Long id) throws NotFoundException {
        UserDTO userDTO = getById(id);
        User user = userconverter.dtoToEntity(userDTO);
        Collection<Booking> list = user.getBookings();
        return bookingConvert.entityToDto(list);
    }

    public Collection<BookingDTO> GetBookingsByUserId(Long id) throws NotFoundException {
        User user = userRepository.getById(id);
        Collection<Booking> list = user.getBookings();
        return bookingConvert.entityToDto(list);
    }


    public void addRoleToUser(String username, String rolename) {
        User appUser = userRepository.findByUsername(username);
        Role appRole = roleRepository.findByRoleName(rolename);
        appUser.getRoles().add(appRole);
    }

    public List<User> FetchUsersByRole(String name) {
        Role role = roleRepository.findByRoleName(name);
        return userRepository.findByRoles_Id(role.getId());
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username not found in database");
        }
        Collection<GrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }


}
