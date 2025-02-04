package com.casavieja.business.enums;

import lombok.Getter;

@Getter
public enum TipoPedidoE {
    POR_USUARIO("1"),
    POR_DISTRIBUIDOR("2"),
    POR_CLIENTE("3");
    public final String value;
    private TipoPedidoE(String value) {
        this.value = value;
    }
    public boolean esIgual(String valor) {
        return this.value.equals(valor);
    }
}
