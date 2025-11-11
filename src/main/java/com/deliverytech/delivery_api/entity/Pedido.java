package com.deliverytech.delivery_api.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_pedido", nullable = false, unique = true)
    private String numeroPedido;

    private String codigo;

    @Column(name = "data_pedido")
    private LocalDateTime dataPedido;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    private BigDecimal total;
    private String enderecoEntrega;
    private String formaPagamento;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "restaurante_id")
    private Restaurante restaurante;

    @PrePersist
    public void gerarNumeroPedido() {
        if (this.numeroPedido == null || this.numeroPedido.isEmpty()) {
            this.numeroPedido = "PED-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
        if (this.dataPedido == null) {
            this.dataPedido = LocalDateTime.now();
        }
    }

    public Pedido(Cliente cliente, LocalDateTime dataPedido, StatusPedido status) {
        this.cliente = cliente;
        this.dataPedido = dataPedido;
        this.status = status;
    }

    public Pedido(LocalDateTime dataPedido, StatusPedido status, BigDecimal total, Cliente cliente,
            Restaurante restaurante) {
        this.dataPedido = dataPedido;
        this.status = status;
        this.total = total;
        this.cliente = cliente;
        this.restaurante = restaurante;
    }
}
