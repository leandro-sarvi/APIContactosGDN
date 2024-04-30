package com.agenda.Contactos.Tienda.dto;

import com.agenda.Contactos.Tienda.util.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class UserDto {
    private String username;
    private String fist_name;
    private String last_name;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFist_name() {
        return fist_name;
    }

    public void setFist_name(String fist_name) {
        this.fist_name = fist_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
