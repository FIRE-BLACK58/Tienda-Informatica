package com.tienda.informatica.application.service;

import com.tienda.informatica.domain.model.ProductoAlquilado;
import com.tienda.informatica.domain.ports.in.ProductoAlquiladoUseCase;
import com.tienda.informatica.domain.ports.out.ProductoAlquiladoRepositoryPort;

import java.util.List;
import java.util.Optional;

public class ProductoAlquiladoService implements ProductoAlquiladoUseCase {

    private final ProductoAlquiladoRepositoryPort repository;

    public ProductoAlquiladoService(ProductoAlquiladoRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public void crearProductoAlquilado(ProductoAlquilado producto) {
        repository.save(producto);
    }

    @Override
    public Optional<ProductoAlquilado> getProductoByCodigo(String codigo) {
        return repository.findByCodigo(codigo);
    }

    @Override
    public List<ProductoAlquilado> getAllProductosAlquilados() {
        return repository.findAll();
    }

    @Override
    public void actualizarProductoAlquilado(ProductoAlquilado producto) {
        repository.update(producto);
    }

    @Override
    public void eliminarProductoAlquilado(String codigo) {
        repository.deleteByCodigo(codigo);
    }
}
