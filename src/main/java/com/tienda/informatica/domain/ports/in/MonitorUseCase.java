package com.tienda.informatica.domain.ports.in;

import com.tienda.informatica.domain.model.Monitor;
import java.util.List;
import java.util.Optional;

public interface MonitorUseCase {
    void crearMonitor(Monitor monitor);
    Optional<Monitor> getMonitorByCodigo(String codigo);
    List<Monitor> getAllMonitores();
    void actualizarMonitor(Monitor monitor);
    void eliminarMonitor(String codigo);
}