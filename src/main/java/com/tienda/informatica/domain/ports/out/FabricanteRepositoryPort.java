package com.tienda.informatica.domain.ports.out;

import com.tienda.informatica.domain.model.Fabricante;
import java.util.List;
import java.util.Optional;

public interface FabricanteRepositoryPort {
    void save(Fabricante fabricante);
    Optional<Fabricante> findById(Integer id);
    List<Fabricante> findAll();
    void update(Fabricante fabricante);
    void deleteById(Integer id);
}