package com.deliverytech.delivery_api.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "restaurantes")
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String categoria;

    @Column(name = "taxa_entrega")
    private BigDecimal taxaEntrega;

    private Boolean ativo;

    private String endereco;
    private String telefone;
    private Double avaliacao;

    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL)
    private List<Produto> produtos;

    public Restaurante(String nome, String categoria, BigDecimal taxaEntrega, Boolean ativo,
            String endereco, String telefone, Double avaliacao) {
        this.nome = nome;
        this.categoria = categoria;
        this.taxaEntrega = taxaEntrega;
        this.ativo = ativo;
        this.endereco = endereco;
        this.telefone = telefone;
        this.avaliacao = avaliacao;
    }
}
