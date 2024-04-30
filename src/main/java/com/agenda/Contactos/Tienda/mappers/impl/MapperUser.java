package com.agenda.Contactos.Tienda.mappers.impl;

import com.agenda.Contactos.Tienda.mappers.IMapper;
import com.agenda.Contactos.Tienda.persistence.entities.User;
import com.agenda.Contactos.Tienda.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class MapperUser implements IMapper<User, UserDto> {
    @Override
    public User map(UserDto o) {
        User us = new User();
        us.setUsername(o.getUsername());
        us.setFist_name(o.getFist_name());
        us.setLast_name(o.getLast_name());
        us.setEmail(o.getEmail());
        us.setPassword(o.getPassword());
        us.setConfirm(false);
        return us;
    }
}
