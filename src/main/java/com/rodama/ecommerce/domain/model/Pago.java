package com.rodama.ecommerce.domain.model;

import java.time.LocalDateTime;

public class Pago {

    private Long idPago;
    private Long pedidoId;
    private String metodo;
    private Double monto;
    private LocalDateTime fecha;

    public Long getId() {
        return idPago;
    }

    public void setId(Long idPago) {
        this.idPago = idPago;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}