package com.deliverytech.delivery_api.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliverytech.delivery_api.dto.RestauranteDTO;
import com.deliverytech.delivery_api.entity.Restaurante;
import com.deliverytech.delivery_api.repository.RestauranteRepository;
import com.deliverytech.projection.RelatorioVendas;

@Service
@Transactional
public class RestauranteService {

    private final RestauranteRepository restauranteRepository;

    public RestauranteService(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    // Cadastrar restaurante
    public RestauranteDTO cadastrar(RestauranteDTO dto) {
        Restaurante restaurante = new Restaurante();
        restaurante.setNome(dto.getNome());
        restaurante.setCategoria(dto.getCategoria());
        restaurante.setEndereco(dto.getEndereco());
        restaurante.setTelefone(dto.getTelefone());
        restaurante.setTaxaEntrega(dto.getTaxaEntrega());
        restaurante.setAvaliacao(dto.getAvaliacao());
        restaurante.setAtivo(true);

        return converterParaDTO(restauranteRepository.save(restaurante));
    }

    @Transactional(readOnly = true)
    public List<RestauranteDTO> listarAtivos() {
        return restauranteRepository.findByAtivoTrue()
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RestauranteDTO buscarPorId(Long id) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado"));
        return converterParaDTO(restaurante);
    }

    public RestauranteDTO atualizar(Long id, RestauranteDTO dto) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado"));

        restaurante.setNome(dto.getNome());
        restaurante.setCategoria(dto.getCategoria());
        restaurante.setEndereco(dto.getEndereco());
        restaurante.setTelefone(dto.getTelefone());
        restaurante.setTaxaEntrega(dto.getTaxaEntrega());
        restaurante.setAvaliacao(dto.getAvaliacao());
        restaurante.setAtivo(dto.getAtivo());

        return converterParaDTO(restauranteRepository.save(restaurante));
    }

    public void inativar(Long id) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado"));
        restaurante.setAtivo(false);
        restauranteRepository.save(restaurante);
    }

    @Transactional(readOnly = true)
    public List<RestauranteDTO> buscarRestaurantesPorCategoria(String categoria) {
        return restauranteRepository.findByCategoriaContainingIgnoreCase(categoria)
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    // ✔ MÉTODO FALTANDO 1
    @Transactional(readOnly = true)
    public List<RestauranteDTO> buscarPorTaxaEntregaMenorOuIgual(BigDecimal taxa) {
        return restauranteRepository.findByTaxaEntregaLessThanEqual(taxa)
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    // ✔ MÉTODO FALTANDO 2
    @Transactional(readOnly = true)
    public List<RestauranteDTO> buscarTop5PorNomeAsc() {
        return restauranteRepository.findTop5ByOrderByNomeAsc()
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    // ✔ MÉTODO FALTANDO 3 (AGORA RETORNA RelatorioVendas)
    @Transactional(readOnly = true)
    public List<RelatorioVendas> relatorioVendasPorRestaurante() {
        return restauranteRepository.relatorioVendasPorRestaurante();
    }

    // Converter para DTO
    private RestauranteDTO converterParaDTO(Restaurante r) {
        RestauranteDTO dto = new RestauranteDTO();
        dto.setId(r.getId());
        dto.setNome(r.getNome());
        dto.setCategoria(r.getCategoria());
        dto.setEndereco(r.getEndereco());
        dto.setTelefone(r.getTelefone());
        dto.setTaxaEntrega(r.getTaxaEntrega());
        dto.setAvaliacao(r.getAvaliacao());
        dto.setAtivo(r.getAtivo());
        return dto;
    }
}
