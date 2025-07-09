package com.tienda.informatica.domain.ports.out;

import com.tienda.informatica.domain.model.Producto;
import java.util.List;
import java.util.Optional;

public interface ProductoRepositoryPort {
    void save(Producto producto);
    Optional<Producto> findByCodigo(String codigo);
    List<Producto> findAll();
    void update(Producto producto);
    void deleteByCodigo(String codigo);
}