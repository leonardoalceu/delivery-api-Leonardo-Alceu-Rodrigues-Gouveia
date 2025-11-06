package com.deliverytech.delivery_api.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliverytech.delivery_api.dto.PedidoDTO;
import com.deliverytech.delivery_api.entity.Pedido;
import com.deliverytech.delivery_api.entity.StatusPedido;
import com.deliverytech.delivery_api.repository.PedidoRepository;

@Service
@Transactional
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    /**
     * Criar novo pedido
     */
    public Pedido criarPedido(PedidoDTO dto) {
        Pedido pedido = new Pedido();

        pedido.setCodigo(dto.getNumeroPedido());
        pedido.setDataPedido(dto.getDataPedido() != null ? dto.getDataPedido() : java.time.LocalDateTime.now());
        pedido.setStatus(StatusPedido.PENDENTE);  // status inicial
        pedido.setTotal(dto.getValorTotal());
        pedido.setEnderecoEntrega(dto.getEnderecoEntrega());
        pedido.setFormaPagamento(dto.getFormaPagamento());

        // Aqui você precisará setar Cliente e Restaurante usando IDs (busca no DB)
        // Exemplo:
        // pedido.setCliente(clienteRepository.findById(dto.getClienteId()).orElseThrow(...));
        // pedido.setRestaurante(restauranteRepository.findById(dto.getRestauranteId()).orElseThrow(...));

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


