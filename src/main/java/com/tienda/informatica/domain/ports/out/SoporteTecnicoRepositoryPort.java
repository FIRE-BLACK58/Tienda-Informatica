package com.tienda.informatica.domain.ports.out;

import com.tienda.informatica.domain.model.SoporteTecnico;
import java.util.List;
import java.util.Optional;

public interface SoporteTecnicoRepositoryPort {
    void save(SoporteTecnico soporte);
    Optional<SoporteTecnico> findById(Integer id);
    List<SoporteTecnico> findAll();
    void update(SoporteTecnico soporte);
    void deleteById(Integer id);
}