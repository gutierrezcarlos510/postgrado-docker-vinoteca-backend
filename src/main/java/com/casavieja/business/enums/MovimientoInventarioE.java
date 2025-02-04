package com.casavieja.business.enums;

import lombok.Getter;

@Getter
public enum MovimientoInventarioE {
    ENTRADA("1"),
    SALIDA("2"),
    TRASPASO_ENTRE_SUCURSALES("3");
    public final String value;
    private MovimientoInventarioE(String val) {
        this.value = val;
    }
    public boolean esIgual(String valor) {
        return this.value.equals(valor);
    }
}
