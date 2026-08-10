package com.rodama.ecommerce.domain.model;

import com.rodama.ecommerce.domain.model.enums.Rol;

public class Usuario {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String password;
    private Rol rol;

    public Usuario(){
    }

    public Usuario(Long id,String nombre,String apellido,String correo,String telefono,String contraseña,Rol rol){
        this.id = id;
        this.nombre = nombre;
        this.email = correo;
        this.telefono = telefono;
        this.password = contraseña;
        this.rol = rol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
