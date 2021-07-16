package com.adservio.reservation.mapper;

import com.adservio.reservation.entities.Booking;
import com.adservio.reservation.entities.dto.BookingDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookingConvert {
    public BookingDTO entityToDto(Booking booking) {


        ModelMapper mapper =new ModelMapper();
        return mapper.map(booking, BookingDTO.class);

    }
    public List<BookingDTO> entityToDto(List<Booking> bookingList) {

        return	bookingList.stream().map(this::entityToDto).collect(Collectors.toList());

    }


    public Booking dtoToEntity(BookingDTO dto)
    {


        ModelMapper mapper = new ModelMapper();

        return mapper.map(dto,Booking.class);
    }

    public List<Booking> dtoToEntity(List<BookingDTO> dto)
    {

        return dto.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }

}


