package modelo;

public class Producto {
    private int codigo;
    private String tipo;
    private String modelo;

    public Producto(int codigo, String tipo, String modelo) {
        this.codigo = codigo;
        this.tipo = tipo;
        this.modelo = modelo;
    }

    public int getCodigo() { return codigo; }
    public String getTipo() { return tipo; }
    public String getModelo() { return modelo; }
}