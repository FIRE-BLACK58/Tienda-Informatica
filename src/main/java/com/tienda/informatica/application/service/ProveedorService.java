package com.tienda.informatica.application.service;

import com.tienda.informatica.domain.model.Proveedor;
import com.tienda.informatica.domain.ports.in.ProveedorUseCase;
import com.tienda.informatica.domain.ports.out.ProveedorRepositoryPort;
import java.util.List;
import java.util.Optional;

public class ProveedorService implements ProveedorUseCase {
    private final ProveedorRepositoryPort proveedorRepository;

    public ProveedorService(ProveedorRepositoryPort proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    @Override
    public void crearProveedor(Proveedor proveedor) {
        proveedorRepository.save(proveedor);
    }

    @Override
    public Optional<Proveedor> getProveedorById(Integer id) {
        return proveedorRepository.findById(id);
    }

    @Override
    public List<Proveedor> getAllProveedores() {
        return proveedorRepository.findAll();
    }

    @Override
    public void actualizarProveedor(Proveedor proveedor) {
        proveedorRepository.update(proveedor);
    }

    @Override
    public void eliminarProveedor(Integer id) {
        proveedorRepository.deleteById(id);
    }
}