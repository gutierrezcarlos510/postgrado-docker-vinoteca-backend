package com.casavieja.business.enums;

import lombok.Getter;

@Getter
public enum CaracteristicaE {
    PRESENTACION_UNIDAD("1"),
    PRESENTACION_CAJA("2"),
    TIPO_CLIENTE("3");
    public final String value;
    private CaracteristicaE(String value) {
        this.value = value;
    }
}
