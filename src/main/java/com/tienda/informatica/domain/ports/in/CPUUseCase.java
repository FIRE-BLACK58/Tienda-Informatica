package com.tienda.informatica.domain.ports.in;

import com.tienda.informatica.domain.model.CPU;
import java.util.List;
import java.util.Optional;

public interface CPUUseCase {
    void crearCPU(CPU cpu);
    Optional<CPU> getCPUByCodigo(String codigo);
    List<CPU> getAllCPUs();
    void actualizarCPU(CPU cpu);
    void eliminarCPU(String codigo);
}