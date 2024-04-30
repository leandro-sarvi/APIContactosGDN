package com.agenda.Contactos.Tienda.services.impl;

import com.agenda.Contactos.Tienda.dto.ContactosDto;
import com.agenda.Contactos.Tienda.mappers.impl.MapperContactos;
import com.agenda.Contactos.Tienda.persistence.entities.Contactos;
import com.agenda.Contactos.Tienda.persistence.repositories.ContactosRepository;
import com.agenda.Contactos.Tienda.services.ContactosService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactosServiceImpl implements ContactosService {
    private ContactosRepository contactosRepository;
    private MapperContactos mapper;
    public ContactosServiceImpl(ContactosRepository contactosRepository, MapperContactos mapper){
        this.contactosRepository = contactosRepository;
        this.mapper = mapper;
    }
    @Override
    public List<Contactos> getFindAllNroTienda(int nro_tienda) {
        List<Contactos> contactosList = this.contactosRepository.findAllByNroTienda(nro_tienda);
        return contactosList;
    }

    @Override
    public List<Contactos> findAllContactos() {
        List<Contactos> contactosList = this.contactosRepository.findAll();
        return contactosList;
    }

    @Override
    public void createContactos(ContactosDto contactosDto) {
        Contactos contacto = this.mapper.map(contactosDto);
        this.contactosRepository.save(contacto);
    }

    @Override
    public void deleteContacto(Long id) {
        this.contactosRepository.deleteById(id);
    }

    @Override
    public Optional<Contactos> findById(Long id) {
        Optional<Contactos> contactoOptional = this.contactosRepository.findById(id);
        return contactoOptional;
    }

}
