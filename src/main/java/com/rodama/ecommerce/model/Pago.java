package com.rodama.ecommerce.model;

import java.lang.reflect.Array;

public class Pago {
    private Long id_pago;
    private Long id_usuario;
    private String metodoDePago;
    private Double total;
    //Aqui va producto

    public Pago(Long id_pago, Long id_usuario, String metodoDePago, Double total) {
        this.id_pago = id_pago;
        this.id_usuario = id_usuario;
        this.metodoDePago = metodoDePago;
        this.total = total;
    }

    public Long getId_pago() {
        return id_pago;
    }

    public void setId_pago(Long id_pago) {
        this.id_pago = id_pago;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getMetodoDePago() {
        return metodoDePago;
    }

    public void setMetodoDePago(String metodoDePago) {
        this.metodoDePago = metodoDePago;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
