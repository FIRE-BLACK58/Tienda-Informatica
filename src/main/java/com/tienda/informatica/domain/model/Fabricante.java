package com.tienda.informatica.domain.model;

public class Fabricante {
    private Integer id;
    private String nombre;
    private String direccion;
    private Integer numeroEmpleados;
    
    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public Integer getNumeroEmpleados() { return numeroEmpleados; }
    public void setNumeroEmpleados(Integer numeroEmpleados) { this.numeroEmpleados = numeroEmpleados; }
}