package com.agenda.Contactos.Tienda.mappers.impl;

import com.agenda.Contactos.Tienda.dto.ContactosDto;
import com.agenda.Contactos.Tienda.mappers.IMapper;
import com.agenda.Contactos.Tienda.persistence.entities.Contactos;
import org.springframework.stereotype.Component;

@Component
public class MapperContactos implements IMapper<Contactos, ContactosDto> {

    @Override
    public Contactos map(ContactosDto o) {
        Contactos contacto = new Contactos();
        contacto.setNombre_contacto(o.nombre_contacto());
        contacto.setStatus_contacto(o.statusContacto());
        contacto.setNro_tienda(o.nro_tienda());
        contacto.setTel(o.tel());
        return contacto;
    }
}
