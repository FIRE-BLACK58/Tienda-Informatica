package com.tienda.informatica.domain.ports.in;

import com.tienda.informatica.domain.model.Impresora;
import java.util.List;
import java.util.Optional;

public interface ImpresoraUseCase {
    void crearImpresora(Impresora impresora);
    Optional<Impresora> getImpresoraByCodigo(String codigo);
    List<Impresora> getAllImpresoras();
    void actualizarImpresora(Impresora impresora);
    void eliminarImpresora(String codigo);
}