package com.deliverytech.delivery_api.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliverytech.delivery_api.dto.RestauranteDTO;
import com.deliverytech.delivery_api.entity.Restaurante;
import com.deliverytech.delivery_api.repository.RestauranteRepository;

@Service
@Transactional
public class RestauranteService {

    private final RestauranteRepository restauranteRepository;

    public RestauranteService(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    /**
     * Cadastrar novo restaurante
     */
    public RestauranteDTO cadastrar(RestauranteDTO dto) {
        Restaurante restaurante = new Restaurante();
        restaurante.setNome(dto.getNome());
        restaurante.setCategoria(dto.getCategoria());
        restaurante.setEndereco(dto.getEndereco());
        restaurante.setTelefone(dto.getTelefone());
        restaurante.setTaxaEntrega(dto.getTaxaEntrega());
        restaurante.setAvaliacao(dto.getAvaliacao());
        restaurante.setAtivo(true);

        Restaurante salvo = restauranteRepository.save(restaurante);
        return converterParaDTO(salvo);
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
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado: " + id));
        return converterParaDTO(restaurante);
    }

    
    public RestauranteDTO atualizar(Long id, RestauranteDTO dto) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado: " + id));

        restaurante.setNome(dto.getNome());
        restaurante.setCategoria(dto.getCategoria());
        restaurante.setEndereco(dto.getEndereco());
        restaurante.setTelefone(dto.getTelefone());
        restaurante.setTaxaEntrega(dto.getTaxaEntrega());
        restaurante.setAvaliacao(dto.getAvaliacao());
        restaurante.setAtivo(dto.getAtivo());

        Restaurante atualizado = restauranteRepository.save(restaurante);
        return converterParaDTO(atualizado);
    }

    
    public void ativarDesativar(Long id) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado: " + id));

        restaurante.setAtivo(!restaurante.getAtivo());
        restauranteRepository.save(restaurante);
    }

    
    @Transactional(readOnly = true)
    public List<RestauranteDTO> buscarRestaurantesPorCategoria(String categoria) {
        return restauranteRepository.findByCategoriaContainingIgnoreCase(categoria)
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    
    public BigDecimal calcularTaxaEntrega(Long id, String cep) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado: " + id));

        
        int ultimoDigito = Character.getNumericValue(cep.charAt(cep.length() - 1));
        BigDecimal adicional = (ultimoDigito % 2 == 0)
                ? BigDecimal.valueOf(2)
                : BigDecimal.valueOf(5);

        return restaurante.getTaxaEntrega().add(adicional);
    }

    
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
