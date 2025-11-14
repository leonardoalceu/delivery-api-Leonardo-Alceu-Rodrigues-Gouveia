package com.deliverytech.delivery_api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.deliverytech.delivery_api.repository.RelatorioRepository;
import com.deliverytech.projection.ClienteMaisAtivo;
import com.deliverytech.projection.ProdutoMaisVendido;
import com.deliverytech.projection.RelatorioVendas;

@Service
public class RelatorioService {

    private final RelatorioRepository relatorioRepository;

    public RelatorioService(RelatorioRepository relatorioRepository) {
        this.relatorioRepository = relatorioRepository;
    }

    public List<RelatorioVendas> vendasPorRestaurante() {
        return relatorioRepository.vendasPorRestaurante();
    }

    public List<ProdutoMaisVendido> produtosMaisVendidos() {
        return relatorioRepository.produtosMaisVendidos();
    }

    public List<ClienteMaisAtivo> clientesMaisAtivos() {
        return relatorioRepository.clientesMaisAtivos();
    }
}
