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
    private String numeroPedido;         
    private LocalDateTime dataPedido;    
    private String status;               
    private BigDecimal valorTotal;       
    private String observacoes;          
    private Long clienteId;              
    private Long restauranteId;          
    private String enderecoEntrega;      
    private String formaPagamento;       
}
