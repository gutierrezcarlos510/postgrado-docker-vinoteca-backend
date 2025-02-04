package com.casavieja.business.enums;

import lombok.Getter;

@Getter
public enum SalidaE {
    ACTIVO("a"),
    FINALIZADO("f"),
    ELIMINADO("e");
    public final String value;
    private SalidaE(String value) {
        this.value = value;
    }
}
