package com.rodama.ecommerce.model;

import java.time.LocalDateTime;

public class Pedido {
    private Long id_pedido;
    private Long id_usuario;
    private String direccion;
    private LocalDateTime fecha;
    private Double precio;

    public Pedido(){}

    public Pedido(Long id_pedido,Long id_usuario,String direccion,LocalDateTime fecha,Double precio){
        this.id_pedido = id_pedido;
        this.id_usuario = id_usuario;
        this.direccion = direccion;
        this.fecha = fecha;
        this.precio = precio;
    }

    public Long getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(Long id_pedido) {
        this.id_pedido = id_pedido;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}
