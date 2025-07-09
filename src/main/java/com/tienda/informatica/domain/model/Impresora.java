package com.tienda.informatica.domain.model;

public class Impresora {
    private String codigoProducto;
    private String tipoTinta;
    private Boolean esMultifuncional;
    
    // Getters y Setters
    public String getCodigoProducto() { return codigoProducto; }
    public void setCodigoProducto(String codigoProducto) { this.codigoProducto = codigoProducto; }
    public String getTipoTinta() { return tipoTinta; }
    public void setTipoTinta(String tipoTinta) { this.tipoTinta = tipoTinta; }
    public Boolean getEsMultifuncional() { return esMultifuncional; }
    public void setEsMultifuncional(Boolean esMultifuncional) { this.esMultifuncional = esMultifuncional; }
}