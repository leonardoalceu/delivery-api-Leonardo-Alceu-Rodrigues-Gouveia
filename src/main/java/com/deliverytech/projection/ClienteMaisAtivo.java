package com.deliverytech.projection;

import java.math.BigDecimal;

public interface ClienteMaisAtivo {
    Long getClienteId();
    String getClienteNome();
    Long getQuantidadePedidos();
    BigDecimal getTotalGasto();
}

