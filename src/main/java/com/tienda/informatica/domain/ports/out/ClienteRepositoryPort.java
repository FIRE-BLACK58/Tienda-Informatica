package com.tienda.informatica.domain.ports.out;

import com.tienda.informatica.domain.model.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteRepositoryPort {
    void save(Cliente cliente);
    Optional<Cliente> findById(Integer id);
    List<Cliente> findAll();
    void update(Cliente cliente);
    void deleteById(Integer id);
}