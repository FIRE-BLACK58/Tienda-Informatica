package com.tienda.informatica.application.service;

import com.tienda.informatica.domain.model.Impresora;
import com.tienda.informatica.domain.ports.in.ImpresoraUseCase;
import com.tienda.informatica.domain.ports.out.ImpresoraRepositoryPort;
import java.util.List;
import java.util.Optional;

public class ImpresoraService implements ImpresoraUseCase {
    private final ImpresoraRepositoryPort impresoraRepository;

    public ImpresoraService(ImpresoraRepositoryPort impresoraRepository) {
        this.impresoraRepository = impresoraRepository;
    }

    @Override
    public void crearImpresora(Impresora impresora) {
        impresoraRepository.save(impresora);
    }

    @Override
    public Optional<Impresora> getImpresoraByCodigo(String codigo) {
        return impresoraRepository.findByCodigo(codigo);
    }

    @Override
    public List<Impresora> getAllImpresoras() {
        return impresoraRepository.findAll();
    }

    @Override
    public void actualizarImpresora(Impresora impresora) {
        impresoraRepository.update(impresora);
    }

    @Override
    public void eliminarImpresora(String codigo) {
        impresoraRepository.deleteByCodigo(codigo);
    }
}