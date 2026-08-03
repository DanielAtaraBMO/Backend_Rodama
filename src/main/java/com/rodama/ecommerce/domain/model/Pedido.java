package com.rodama.ecommerce.domain.model;

import java.time.LocalDateTime;

public class Pedido {
    private Long id_pedido;
    private Usuario usuario;
    private String direccion;
    private LocalDateTime fecha;
    private Double precio;

    public Pedido(){}

    public Pedido(Long id_pedido,Usuario usuario,String direccion,LocalDateTime fecha,Double precio){
        this.id_pedido = id_pedido;
        this.usuario = usuario;
        this.direccion = direccion;
        this.fecha = fecha;
        this.precio = precio;
    }

    public Long getIdPedido() {
        return id_pedido;
    }

    public void setIdPedido(Long id_pedido) {
        this.id_pedido = id_pedido;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
