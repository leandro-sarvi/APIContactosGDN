package com.agenda.Contactos.Tienda.persistence.repositories;

import com.agenda.Contactos.Tienda.persistence.entities.Contactos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactosRepository extends JpaRepository<Contactos, Long> {
    // Filtramos por nro de tienda
    @Query(value = "SELECT * FROM contactos_tienda WHERE nro_tienda=:nro_tienda",nativeQuery = true)
    public List<Contactos> findAllByNroTienda(@Param("nro_tienda") int nro_tienda);
}
