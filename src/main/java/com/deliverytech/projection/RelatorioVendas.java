package com.deliverytech.projection;


import java.math.BigDecimal;

public interface RelatorioVendas {

    Long getRestauranteId();
    String getRestauranteNome();
    Long getQuantidadePedidos();
    BigDecimal getTotalVendas();
}//ja existe
