package com.agenda.Contactos.Tienda.persistence.repositories;

import com.agenda.Contactos.Tienda.persistence.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Filtraremos por email
    @Query(value = "SELECT * FROM _user WHERE email=:email",nativeQuery = true)
    Optional<User> findByEmail(@Param("email") String email);
    // Filtraremos por Role
    /* *Una lista puede ser nula si no hay resultados */
    @Query(value = "SELECT * FROM _user WHERE role=:role",nativeQuery = true)
    List<User> findAllByRole(@Param("role") String role);
    @Query(value = "SELECT * FROM _user WHERE id=:id",nativeQuery = true)
    Optional<User> findUserById(@Param("id") Long id);
    @Query(value = "SELECT * FROM _user WHERE username=:username",nativeQuery = true)
    Optional<User> findUserByUserName(@Param("username") String username);
    @Query(value = "SELECT * FROM _user WHERE token_confirm=:token_confirm",nativeQuery = true)
    Optional<User> findByTokenConfirm(@Param("token_confirm") String tokenConfirm);
    @Transactional
    @Modifying
    @Query(value = "UPDATE _user SET confirm = true  WHERE token_confirm =:token_confirm",nativeQuery = true)
    void confirmEmail(@Param("token_confirm") String tokenConfirm);
}
