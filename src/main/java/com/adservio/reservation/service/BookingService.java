package com.adservio.reservation.service;

import com.adservio.reservation.dao.BookingRepository;
import com.adservio.reservation.dao.RoomRepository;
import com.adservio.reservation.dao.UserRepository;
import com.adservio.reservation.dto.UserDTO;
import com.adservio.reservation.entities.Booking;
import com.adservio.reservation.entities.Room;
import com.adservio.reservation.dto.BookingDTO;
import com.adservio.reservation.exception.NotFoundException;
import com.adservio.reservation.mapper.BookingConvert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class BookingService {
   public final
    BookingRepository bookingRepository;
    public final
    BookingConvert converter;
    public final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public List<BookingDTO> listAll(){

        return converter.entityToDto(bookingRepository.findAll());
    }

    public BookingDTO getById(Long id) throws NotFoundException {
        Optional<Booking> booking=bookingRepository.findById(id);
        if(booking.isEmpty()) throw new NotFoundException("Reservation Not Available");
        return converter.entityToDto(booking.get());
    }

    public BookingDTO getBookingByCode(String code) {
        return converter.entityToDto(bookingRepository.findByCode(code));
    }
    public UserDTO GetUserByBookingId(Long id) throws NotFoundException {
        BookingDTO bookingDTO=getById(id);
        UserDTO user=bookingDTO.getUser();
        return user;
    }

    public BookingDTO save(BookingDTO bookingDTO) throws NotFoundException {
        bookingDTO.setCode(UUID.randomUUID().toString());
        Booking booking=converter.dtoToEntity(bookingDTO);
        Room room=roomRepository.checkAvailability(booking.getStartDate(),booking.getEndDate(),booking.getRoom().getId());
        if(room!=null){
            booking=bookingRepository.save(booking);
            return converter.entityToDto(booking);
        }
        else {
            throw new NotFoundException("Room already in use");
        }

    }

    public List<BookingDTO> saveDepartments(List<BookingDTO> bookingDTOS){
        List<Booking> bookings=converter.dtoToEntity(bookingDTOS);
        bookings=bookingRepository.saveAll(bookings);
        return converter.entityToDto(bookings);

    }

    public String deleteBooking(Long id){
        bookingRepository.deleteById(id);
        return "Deleted successfully";
    }
}
