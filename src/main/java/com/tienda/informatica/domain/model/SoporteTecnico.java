package com.tienda.informatica.domain.model;

import java.time.LocalDate;

public class SoporteTecnico {
    private Integer id;
    private Integer clienteId;
    private Integer productoId;
    private LocalDate fechaSoporte;
    private String descripcionProblema;
    private String solucion;
    
    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getClienteId() { return clienteId; }
    public void setClienteId(Integer clienteId) { this.clienteId = clienteId; }
    public Integer getProductoId() { return productoId; }
    public void setProductoId(Integer productoId) { this.productoId = productoId; }
    public LocalDate getFechaSoporte() { return fechaSoporte; }
    public void setFechaSoporte(LocalDate fechaSoporte) { this.fechaSoporte = fechaSoporte; }
    public String getDescripcionProblema() { return descripcionProblema; }
    public void setDescripcionProblema(String descripcionProblema) { this.descripcionProblema = descripcionProblema; }
    public String getSolucion() { return solucion; }
    public void setSolucion(String solucion) { this.solucion = solucion; }
}