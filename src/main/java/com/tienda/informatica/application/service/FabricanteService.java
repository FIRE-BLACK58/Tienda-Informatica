package com.tienda.informatica.application.service;

import com.tienda.informatica.domain.model.Fabricante;
import com.tienda.informatica.domain.ports.in.FabricanteUseCase;
import com.tienda.informatica.domain.ports.out.FabricanteRepositoryPort;
import java.util.List;
import java.util.Optional;

public class FabricanteService implements FabricanteUseCase {
    private final FabricanteRepositoryPort fabricanteRepository;

    public FabricanteService(FabricanteRepositoryPort fabricanteRepository) {
        this.fabricanteRepository = fabricanteRepository;
    }

    @Override
    public void crearFabricante(Fabricante fabricante) {
        fabricanteRepository.save(fabricante);
    }

    @Override
    public Optional<Fabricante> getFabricanteById(Integer id) {
        return fabricanteRepository.findById(id);
    }

    @Override
    public List<Fabricante> getAllFabricantes() {
        return fabricanteRepository.findAll();
    }

    @Override
    public void actualizarFabricante(Fabricante fabricante) {
        fabricanteRepository.update(fabricante);
    }

    @Override
    public void eliminarFabricante(Integer id) {
        fabricanteRepository.deleteById(id);
    }
}