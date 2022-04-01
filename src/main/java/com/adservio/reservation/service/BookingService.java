package com.adservio.reservation.service;

import com.adservio.reservation.dao.BookingRepository;
import com.adservio.reservation.dao.RoomRepository;
import com.adservio.reservation.dao.UserRepository;
import com.adservio.reservation.dto.BookingDTO;
import com.adservio.reservation.dto.UserDTO;
import com.adservio.reservation.entities.Booking;
import com.adservio.reservation.entities.Room;
import com.adservio.reservation.exception.NotFoundException;
import com.adservio.reservation.mapper.BookingConvert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final BookingConvert converter;
    private final RoomRepository roomRepository;


    public List<BookingDTO> listAll() {

        return converter.entityToDto(bookingRepository.findAll());
    }


    public void DeleteByCode(String code) {
        bookingRepository.deleteByCode(code);
    }

    public BookingDTO getById(Long id) throws NotFoundException {
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isEmpty()) throw new NotFoundException("Reservation Not Available");
        return converter.entityToDto(booking.get());
    }

    public BookingDTO getBookingByCode(String code) {
        return converter.entityToDto(bookingRepository.findByCode(code));
    }

    public BookingDTO bookRoom(String Name, LocalDateTime Start, LocalDateTime End) {
        Booking booking = new Booking();
        booking.setRoom(roomRepository.findByName(Name));
        booking.setStartDate(Start);
        booking.setEndDate(End);
        booking.setCode(UUID.randomUUID().toString());
        return converter.entityToDto(booking);
    }

    public String NextBooking() {
        List<Booking> bookings = bookingRepository.findAll();
        Booking next = new Booking();
        int minvalue = 100000000;
        for (Booking booking : bookings) {
            if (booking.getStartDate().isAfter(LocalDateTime.now()) && booking.isConfirmed()) {
                int duration = (int) Duration.between(LocalDateTime.now(), booking.getStartDate()).toMinutes();
                if (duration < minvalue) {
                    minvalue = (int) duration;
                    next = booking;
                }
                System.out.println(duration);
            }
        }
        return next.getCode();
    }

    public Collection<BookingDTO> GetAllByRoomName(String name) throws NotFoundException {
        Room room = roomRepository.findByName(name);
        if (Objects.isNull(room)) {
            throw new NotFoundException("Room not found");
        } else {

            Collection<Booking> bookings = room.getBookings();
            return converter.entityToDto(bookings);
        }
    }

    public UserDTO GetUserByBookingId(Long id) throws NotFoundException {
        BookingDTO bookingDTO = getById(id);
        return bookingDTO.getUser();
    }


    public BookingDTO save(BookingDTO bookingDTO) throws NotFoundException {
        Booking booking = converter.dtoToEntity(bookingDTO);
        booking.setCode(UUID.randomUUID().toString());

        Room room = roomRepository.checkAvailability(booking.getStartDate(), booking.getEndDate(), booking.getRoom().getId());
        if (room != null) {
            booking = bookingRepository.save(booking);
            return converter.entityToDto(booking);
        } else {
            throw new NotFoundException("Room already in use");
        }

    }


    public void confirmBooking(Long id, boolean isconfirmed) throws NotFoundException {
        BookingDTO bookingDTO = getById(id);
        Booking booking = converter.dtoToEntity(bookingDTO);
        booking.setConfirmed(isconfirmed);
        bookingRepository.save(booking);
        booking.getRoom().setCounter(booking.getRoom().getCounter() + 1);
        roomRepository.save(booking.getRoom());

    }

    public List<BookingDTO> saveDepartments(List<BookingDTO> bookingDTOS) {
        List<Booking> bookings = converter.dtoToEntity(bookingDTOS);
        bookings = bookingRepository.saveAll(bookings);
        return converter.entityToDto(bookings);

    }

    public String deleteBooking(Long id) {
        Booking booking = bookingRepository.getById(id);
        bookingRepository.deleteById(id);
        return "Deleted successfully";
    }


}
