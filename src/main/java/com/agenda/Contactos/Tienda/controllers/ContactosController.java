package com.agenda.Contactos.Tienda.controllers;

import com.agenda.Contactos.Tienda.dto.ContactosDto;
import com.agenda.Contactos.Tienda.exceptions.BadRequestException;
import com.agenda.Contactos.Tienda.exceptions.ResourceNotFoundException;
import com.agenda.Contactos.Tienda.persistence.entities.Contactos;
import com.agenda.Contactos.Tienda.services.impl.ContactosServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ContactosController {
    private final ContactosServiceImpl contactosService;
    public ContactosController(ContactosServiceImpl contactosService){
        this.contactosService = contactosService;
    }
    @GetMapping("/prueba")
    public String getContactos(){
        return "Tiene acceso";
    }
    @GetMapping("/contactos")
    public List<Contactos> findAllContactos(){
        List<Contactos> contactosList = this.contactosService.findAllContactos();
        return contactosList;
    }
    @GetMapping("/contactos/findbynrotienda{nro_tienda}")
    public List<Contactos> findAllByNroTienda(@PathVariable("nro_tienda") int nro_tienda){
        List<Contactos> contactosList = this.contactosService.getFindAllNroTienda(nro_tienda);
        if(contactosList.isEmpty()){
            throw new ResourceNotFoundException("Los contactos no fueron encontrados por numero de tienda ingresado");
        }
        return contactosList;
    }
    @GetMapping("/contactos/findbyid/{id}")
    public ResponseEntity<Optional<Contactos>> findById(@PathVariable("id") long id){
        Optional<Contactos> contactosOptional = this.contactosService.findById(id);
        if(contactosOptional.isEmpty()){
            throw new ResourceNotFoundException("Contacto no encontrado por ID");
        }
        return ResponseEntity.status(HttpStatus.OK).body(contactosOptional);
    }
    @PostMapping("/contactoscreate")
    public ResponseEntity<String> createdContactos(@RequestBody ContactosDto contactosDto){
        if(contactosDto.nombre_contacto().isBlank()||contactosDto.tel()==0||contactosDto.nro_tienda()==0){
            throw new BadRequestException("Campos vacios o nulos, se deben incluir");
        }
        this.contactosService.createContactos(contactosDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping("/contactos/deletebyid/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") long id){
        Optional<Contactos> optionalContactos = this.contactosService.findById(id);
        if(optionalContactos.isEmpty()){
            throw new ResourceNotFoundException("El contacto no fue encontrado por ID");
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
