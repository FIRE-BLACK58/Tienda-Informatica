package com.tienda.informatica.application.service;

import com.tienda.informatica.domain.model.SoporteTecnico;
import com.tienda.informatica.domain.ports.in.SoporteTecnicoUseCase;
import com.tienda.informatica.domain.ports.out.SoporteTecnicoRepositoryPort;
import java.util.List;
import java.util.Optional;

public class SoporteTecnicoService implements SoporteTecnicoUseCase {
    private final SoporteTecnicoRepositoryPort soporteRepository;

    public SoporteTecnicoService(SoporteTecnicoRepositoryPort soporteRepository) {
        this.soporteRepository = soporteRepository;
    }

    @Override
    public void crearSoporteTecnico(SoporteTecnico soporte) {
        soporteRepository.save(soporte);
    }

    @Override
    public Optional<SoporteTecnico> getSoporteTecnicoById(Integer id) {
        return soporteRepository.findById(id);
    }

    @Override
    public List<SoporteTecnico> getAllSoportesTecnicos() {
        return soporteRepository.findAll();
    }

    @Override
    public void actualizarSoporteTecnico(SoporteTecnico soporte) {
        soporteRepository.update(soporte);
    }

    @Override
    public void eliminarSoporteTecnico(Integer id) {
        soporteRepository.deleteById(id);
    }
}