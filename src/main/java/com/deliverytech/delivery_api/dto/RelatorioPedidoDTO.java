package com.deliverytech.delivery_api.dto;

import java.math.BigDecimal;

public class RelatorioPedidoDTO {

    private Long pedidoId;
    private String clienteNome;
    private BigDecimal valorTotal;

    // Construtor
    public RelatorioPedidoDTO(Long pedidoId, String clienteNome, BigDecimal valorTotal) {
        this.pedidoId = pedidoId;
        this.clienteNome = clienteNome;
        this.valorTotal = valorTotal;
    }

    // Getters
    public Long getPedidoId() {
        return pedidoId;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    // Setters (opcional)
    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}
