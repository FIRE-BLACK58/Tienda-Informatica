package com.tienda.informatica.application.service;

import com.tienda.informatica.domain.model.CPU;
import com.tienda.informatica.domain.ports.in.CPUUseCase;
import com.tienda.informatica.domain.ports.out.CPURepositoryPort;
import java.util.List;
import java.util.Optional;

public class CPUService implements CPUUseCase {
    private final CPURepositoryPort cpuRepository;

    public CPUService(CPURepositoryPort cpuRepository) {
        this.cpuRepository = cpuRepository;
    }

    @Override
    public void crearCPU(CPU cpu) {
        cpuRepository.save(cpu);
    }

    @Override
    public Optional<CPU> getCPUByCodigo(String codigo) {
        return cpuRepository.findByCodigo(codigo);
    }

    @Override
    public List<CPU> getAllCPUs() {
        return cpuRepository.findAll();
    }

    @Override
    public void actualizarCPU(CPU cpu) {
        cpuRepository.update(cpu);
    }

    @Override
    public void eliminarCPU(String codigo) {
        cpuRepository.deleteByCodigo(codigo);
    }
}