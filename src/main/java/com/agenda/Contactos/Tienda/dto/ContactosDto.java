package com.agenda.Contactos.Tienda.dto;

import com.agenda.Contactos.Tienda.persistence.entities.Status_contacto;
public record ContactosDto
   (
    Status_contacto statusContacto,
    String nombre_contacto,
    int nro_tienda,
    long tel
           ){

}

