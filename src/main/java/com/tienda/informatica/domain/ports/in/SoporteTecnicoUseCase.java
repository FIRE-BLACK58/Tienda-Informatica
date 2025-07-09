package com.tienda.informatica.domain.ports.in;

import com.tienda.informatica.domain.model.SoporteTecnico;
import java.util.List;
import java.util.Optional;

public interface SoporteTecnicoUseCase {
    void crearSoporteTecnico(SoporteTecnico soporte);
    Optional<SoporteTecnico> getSoporteTecnicoById(Integer id);
    List<SoporteTecnico> getAllSoportesTecnicos();
    void actualizarSoporteTecnico(SoporteTecnico soporte);
    void eliminarSoporteTecnico(Integer id);
}