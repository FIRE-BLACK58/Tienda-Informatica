package com.tienda.informatica.application.service;

import com.tienda.informatica.domain.model.Producto;
import com.tienda.informatica.domain.ports.in.ProductoUseCase;
import com.tienda.informatica.domain.ports.out.ProductoRepositoryPort;
import java.util.List;
import java.util.Optional;

public class ProductoService implements ProductoUseCase {
    private final ProductoRepositoryPort productoRepositoryPort;

    public ProductoService(ProductoRepositoryPort productoRepositoryPort) {
        this.productoRepositoryPort = productoRepositoryPort;
    }

    @Override
    public void crearProducto(Producto producto) {
        productoRepositoryPort.save(producto);
    }

    @Override
    public Optional<Producto> getProductoByCodigo(String codigo) {
        return productoRepositoryPort.findByCodigo(codigo);
    }

    @Override
    public List<Producto> getAllProductos() {
        return productoRepositoryPort.findAll();
    }

    @Override
    public void actualizarProducto(Producto producto) {
        productoRepositoryPort.update(producto);
    }

    @Override
    public void eliminarProducto(String codigo) {
        productoRepositoryPort.deleteByCodigo(codigo);
    }
}