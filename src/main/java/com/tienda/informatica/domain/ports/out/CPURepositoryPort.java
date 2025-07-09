package com.tienda.informatica.domain.ports.out;

import com.tienda.informatica.domain.model.CPU;
import java.util.List;
import java.util.Optional;

public interface CPURepositoryPort {
    void save(CPU cpu);
    Optional<CPU> findByCodigo(String codigo);
    List<CPU> findAll();
    void update(CPU cpu);
    void deleteByCodigo(String codigo);
}