package com.casavieja.business.enums;

import lombok.Getter;

@Getter
public enum TipoVentaE {
    CONTADO("1"),
    CREDITO("2"),
    REGALO("3");
    public final String value;
    private TipoVentaE(String value) {
        this.value = value;
    }
}
