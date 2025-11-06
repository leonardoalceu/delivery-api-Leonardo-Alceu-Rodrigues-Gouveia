package com.deliverytech.delivery_api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

    private Long id;
    private String numeroPedido;         // código do pedido
    private LocalDateTime dataPedido;    // data do pedido
    private String status;               // status do pedido (PENDENTE, CONFIRMADO, etc.)
    private BigDecimal valorTotal;       // total do pedido
    private String observacoes;          // observações opcionais
    private Long clienteId;              // id do cliente
    private Long restauranteId;          // id do restaurante
    private String enderecoEntrega;      // endereço de entrega
    private String formaPagamento;       // forma de pagamento (opcional)
}
