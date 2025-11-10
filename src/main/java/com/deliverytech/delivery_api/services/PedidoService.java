package com.deliverytech.delivery_api.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliverytech.delivery_api.dto.PedidoDTO;
import com.deliverytech.delivery_api.entity.Pedido;
import com.deliverytech.delivery_api.entity.StatusPedido;
import com.deliverytech.delivery_api.repository.ClienteRepository;
import com.deliverytech.delivery_api.repository.PedidoRepository;
import com.deliverytech.delivery_api.repository.RestauranteRepository;

@Service
@Transactional
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final RestauranteRepository restauranteRepository;

    public PedidoService(PedidoRepository pedidoRepository,
                         ClienteRepository clienteRepository,
                         RestauranteRepository restauranteRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.restauranteRepository = restauranteRepository;
    }

    /**
     * Criar novo pedido
     */
    public Pedido criarPedido(PedidoDTO dto) {
        Pedido pedido = new Pedido();

        pedido.setCodigo(dto.getNumeroPedido());
        pedido.setDataPedido(dto.getDataPedido() != null ? dto.getDataPedido() : java.time.LocalDateTime.now());
        pedido.setStatus(StatusPedido.PENDENTE);
        pedido.setTotal(dto.getValorTotal());
        pedido.setEnderecoEntrega(dto.getEnderecoEntrega());
        pedido.setFormaPagamento(dto.getFormaPagamento());

        // Buscar Cliente pelo ID
        pedido.setCliente(clienteRepository
                .findById(dto.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado")));

        // Buscar Restaurante pelo ID
        pedido.setRestaurante(restauranteRepository
                .findById(dto.getRestauranteId())
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado")));

        return pedidoRepository.save(pedido);
    }

    /**
     * Listar pedidos por cliente
     */
    @Transactional(readOnly = true)
    public List<Pedido> listarPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteIdOrderByDataPedidoDesc(clienteId);
    }

    /**
     * Atualizar status do pedido
     */
    public Pedido atualizarStatus(Long pedidoId, StatusPedido status) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
        pedido.setStatus(status);
        return pedidoRepository.save(pedido);
    }
}
