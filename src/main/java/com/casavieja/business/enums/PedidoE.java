package com.casavieja.business.enums;

import lombok.Getter;

@Getter
public enum PedidoE {
    RECHAZADO("0"),
    PEDIDO_RECIBIDO("1"),
    EN_PROCESO("2"),
    ENTREGADO("3");
    public String value;
    private PedidoE(String value) {
        this.value = value;
    }
    public boolean esIgual(String valor) {
        return this.value.equals(valor);
    }
}
