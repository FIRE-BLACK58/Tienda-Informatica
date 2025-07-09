package com.tienda.informatica.domain.ports.out;

import com.tienda.informatica.domain.model.Monitor;
import java.util.List;
import java.util.Optional;

public interface MonitorRepositoryPort {
    void save(Monitor monitor);
    Optional<Monitor> findByCodigo(String codigo);
    List<Monitor> findAll();
    void update(Monitor monitor);
    void deleteByCodigo(String codigo);
}