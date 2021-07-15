package com.adservio.reservation.service;

import com.adservio.reservation.dao.BookingRepository;
import com.adservio.reservation.entities.Booking;
import com.adservio.reservation.entities.dto.BookingDTO;
import com.adservio.reservation.mapper.BookingConvert;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class BookingService {
    final
    BookingRepository bookingRepository;
    final
    BookingConvert converter;

    public BookingService(BookingRepository bookingRepository, BookingConvert converter) {
        this.bookingRepository = bookingRepository;
        this.converter = converter;
    }

    public List<BookingDTO> listAll(){

        return converter.entityToDto(bookingRepository.findAll());
    }

    public BookingDTO getById(Long id){

        return converter.entityToDto(bookingRepository.findById(id).orElse(null));
    }

    public BookingDTO getBookingByCode(String code) {
        return converter.entityToDto(bookingRepository.findByCode(code));
    }


    public BookingDTO save(BookingDTO bookingDTO){
        bookingDTO.setBookingCode(UUID.randomUUID().toString());
        Booking booking=converter.dtoToEntity(bookingDTO);
        booking=bookingRepository.save(booking);
        return converter.entityToDto(booking);
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
