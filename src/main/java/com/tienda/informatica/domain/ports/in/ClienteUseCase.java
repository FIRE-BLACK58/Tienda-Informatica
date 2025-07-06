package com.tienda.informatica.domain.ports.in;

import com.tienda.informatica.domain.model.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteUseCase {
    void crearCliente(Cliente cliente);
    Optional<Cliente> getClienteById(Integer id);
    List<Cliente> getAllClientes();
    void actualizarCliente(Cliente cliente);
    void eliminarCliente(Integer id);
}