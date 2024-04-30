package com.agenda.Contactos.Tienda.persistence.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "contactos_tienda")
@Data
public class Contactos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int nro_tienda;
    private String nombre_contacto;
    private long tel;
    @Enumerated(EnumType.STRING)
    private Status_contacto status_contacto;
}
