package com.adservio.reservation.service;

import com.adservio.reservation.dao.BookingRepository;
import com.adservio.reservation.dao.RoomRepository;
import com.adservio.reservation.dto.BookingDTO;
import com.adservio.reservation.dto.UserDTO;
import com.adservio.reservation.entities.Booking;
import com.adservio.reservation.entities.Room;
import com.adservio.reservation.exception.NotFoundException;
import com.adservio.reservation.mapper.BookingConvert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class BookingService {
    public final
    BookingRepository bookingRepository;
    public final
    BookingConvert converter;
    public final RoomRepository roomRepository;


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
        booking.getRoom().setReserved(true);
        roomRepository.save(booking.getRoom());
        bookingRepository.save(booking);
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