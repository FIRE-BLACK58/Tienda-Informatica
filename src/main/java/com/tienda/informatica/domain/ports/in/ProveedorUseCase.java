package com.tienda.informatica.domain.ports.in;

import com.tienda.informatica.domain.model.Proveedor;
import java.util.List;
import java.util.Optional;

public interface ProveedorUseCase {
    void crearProveedor(Proveedor proveedor);
    Optional<Proveedor> getProveedorById(Integer id);
    List<Proveedor> getAllProveedores();
    void actualizarProveedor(Proveedor proveedor);
    void eliminarProveedor(Integer id);
}