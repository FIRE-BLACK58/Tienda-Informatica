package com.tienda.informatica.domain.ports.out;

import com.tienda.informatica.domain.model.Proveedor;
import java.util.List;
import java.util.Optional;

public interface ProveedorRepositoryPort {
    void save(Proveedor proveedor);
    Optional<Proveedor> findById(Integer id);
    List<Proveedor> findAll();
    void update(Proveedor proveedor);
    void deleteById(Integer id);
}