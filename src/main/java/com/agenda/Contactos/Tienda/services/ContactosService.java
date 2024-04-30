package com.agenda.Contactos.Tienda.services;

import com.agenda.Contactos.Tienda.dto.ContactosDto;
import com.agenda.Contactos.Tienda.persistence.entities.Contactos;

import java.util.List;
import java.util.Optional;

public interface ContactosService{
    Optional<Contactos> findById(Long id);
    List<Contactos> getFindAllNroTienda(int nro_tienda);
    List<Contactos> findAllContactos();
    void createContactos(ContactosDto contactosDto);
    void deleteContacto(Long id);
}
