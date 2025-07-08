package com.tienda.informatica.application.service;

import com.tienda.informatica.domain.model.Monitor;
import com.tienda.informatica.domain.ports.in.MonitorUseCase;
import com.tienda.informatica.domain.ports.out.MonitorRepositoryPort;
import java.util.List;
import java.util.Optional;

public class MonitorService implements MonitorUseCase {
    private final MonitorRepositoryPort monitorRepository;

    public MonitorService(MonitorRepositoryPort monitorRepository) {
        this.monitorRepository = monitorRepository;
    }

    @Override
    public void crearMonitor(Monitor monitor) {
        monitorRepository.save(monitor);
    }

    @Override
    public Optional<Monitor> getMonitorByCodigo(String codigo) {
        return monitorRepository.findByCodigo(codigo);
    }

    @Override
    public List<Monitor> getAllMonitores() {
        return monitorRepository.findAll();
    }

    @Override
    public void actualizarMonitor(Monitor monitor) {
        monitorRepository.update(monitor);
    }

    @Override
    public void eliminarMonitor(String codigo) {
        monitorRepository.deleteByCodigo(codigo);
    }
}