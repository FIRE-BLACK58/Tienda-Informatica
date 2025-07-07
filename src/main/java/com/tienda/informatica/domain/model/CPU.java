package com.tienda.informatica.domain.model;

public class CPU {
    private String codigoProducto;
    private String memoriaPrincipal;
    private String velocidad;
    
    // Getters y Setters
    public String getCodigoProducto() { return codigoProducto; }
    public void setCodigoProducto(String codigoProducto) { this.codigoProducto = codigoProducto; }
    public String getMemoriaPrincipal() { return memoriaPrincipal; }
    public void setMemoriaPrincipal(String memoriaPrincipal) { this.memoriaPrincipal = memoriaPrincipal; }
    public String getVelocidad() { return velocidad; }
    public void setVelocidad(String velocidad) { this.velocidad = velocidad; }
}