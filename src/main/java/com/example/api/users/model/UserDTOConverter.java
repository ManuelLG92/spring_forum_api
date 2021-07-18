package com.example.api.users.model;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserDTOConverter {

    private final ModelMapper mapper;

    public UserDTOConverter(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public UserDTO makeUserDTO(User user) {
        return  mapper.map(user,UserDTO.class);
    }
}
