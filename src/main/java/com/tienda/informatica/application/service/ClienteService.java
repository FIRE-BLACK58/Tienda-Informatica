package com.tienda.informatica.application.service;

import com.tienda.informatica.domain.model.Cliente;
import com.tienda.informatica.domain.ports.in.ClienteUseCase;
import com.tienda.informatica.domain.ports.out.ClienteRepositoryPort;
import java.util.List;
import java.util.Optional;

public class ClienteService implements ClienteUseCase {
    private final ClienteRepositoryPort clienteRepository;

    public ClienteService(ClienteRepositoryPort clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void crearCliente(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    @Override
    public Optional<Cliente> getClienteById(Integer id) {
        return clienteRepository.findById(id);
    }

    @Override
    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public void actualizarCliente(Cliente cliente) {
        clienteRepository.update(cliente);
    }

    @Override
    public void eliminarCliente(Integer id) {
        clienteRepository.deleteById(id);
    }
}