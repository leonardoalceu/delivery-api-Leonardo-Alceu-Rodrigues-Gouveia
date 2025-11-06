package com.deliverytech.delivery_api.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor; // ← importar BigDecimal

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestauranteDTO {

    private Long id;
    private String nome;
    private String categoria;
    private String endereco;
    private String telefone;
    private BigDecimal taxaEntrega;  // mesmo tipo da entidade
    private Double avaliacao;        // mesmo tipo da entidade
    private Boolean ativo;

}
