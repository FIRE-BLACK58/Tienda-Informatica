package com.tienda.informatica.domain.ports.out;

import com.tienda.informatica.domain.model.Impresora;
import java.util.List;
import java.util.Optional;

public interface ImpresoraRepositoryPort {
    void save(Impresora impresora);
    Optional<Impresora> findByCodigo(String codigo);
    List<Impresora> findAll();
    void update(Impresora impresora);
    void deleteByCodigo(String codigo);
}