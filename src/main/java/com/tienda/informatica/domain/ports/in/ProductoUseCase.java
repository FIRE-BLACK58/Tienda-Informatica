package com.tienda.informatica.domain.ports.in;

import com.tienda.informatica.domain.model.Producto;
import java.util.List;
import java.util.Optional;

public interface ProductoUseCase {
    void crearProducto(Producto producto);
    Optional<Producto> getProductoByCodigo(String codigo);
    List<Producto> getAllProductos();
    void actualizarProducto(Producto producto);
    void eliminarProducto(String codigo);
}