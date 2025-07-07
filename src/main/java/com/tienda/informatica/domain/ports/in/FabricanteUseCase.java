package com.tienda.informatica.domain.ports.in;

import com.tienda.informatica.domain.model.Fabricante;
import java.util.List;
import java.util.Optional;

public interface FabricanteUseCase {
    void crearFabricante(Fabricante fabricante);
    Optional<Fabricante> getFabricanteById(Integer id);
    List<Fabricante> getAllFabricantes();
    void actualizarFabricante(Fabricante fabricante);
    void eliminarFabricante(Integer id);
}