package com.casavieja.business.enums;

import lombok.Getter;

@Getter
public enum FormaPagoE {
    EFECTIVO(1),
    QR(2),
    TIGO_MONEY(3);
    public final int value;
    private FormaPagoE(int value) {
        this.value = value;
    }
}
