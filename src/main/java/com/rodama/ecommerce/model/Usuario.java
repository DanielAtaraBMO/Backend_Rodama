package com.rodama.ecommerce.model;

import com.rodama.ecommerce.model.enums.Rol;
import jakarta.persistence.Id;

public class Usuario {
    private Long id;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String contraseña;
    private Rol rol;

    private Usuario(){

    }
    private Usuario(Long id,String nombre,String apellido,String correo,String telefono,String contraseña,Rol rol){
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.contraseña = contraseña;
        this.rol = rol;
    }


}
