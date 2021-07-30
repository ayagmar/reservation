package com.adservio.reservation.mapper;


import com.adservio.reservation.entities.User;
import com.adservio.reservation.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class UserConvert {
    public UserDTO entityToDto(User user){
        ModelMapper mapper=new ModelMapper();
        return mapper.map(user,UserDTO.class);
    }

    public List<UserDTO> entityToDto(List<User> user) {

        return	user.stream().map(this::entityToDto).collect(Collectors.toList());

    }

    public Collection<UserDTO> entityToDto(Collection<User> list) {
        return  list.stream().map(this::entityToDto).collect(Collectors.toList());

    }

    public User dtoToEntity(UserDTO dto){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(dto, User.class);

    }

    public List<User> dtoToEntity(List<UserDTO> dto)
    {

        return dto.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }

}
