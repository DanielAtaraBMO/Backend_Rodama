package com.rodama.ecommerce.infrastructure.entity;

import com.rodama.ecommerce.domain.model.enums.Rol;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String apellido;

    private String email;

    private String telefono;

    private String password;

    @Column(name = "rol")
    private String rolRaw;

    public Rol getRol() {
        if (rolRaw == null || rolRaw.trim().isEmpty()) return Rol.ROLE_USER;
        String upper = rolRaw.trim().toUpperCase();
        if (upper.contains("ADMIN")) return Rol.ROLE_ADMIN;
        return Rol.ROLE_USER;
    }

    public void setRol(Rol rol) {
        this.rolRaw = rol != null ? rol.name() : Rol.ROLE_USER.name();
    }
}