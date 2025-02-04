package com.casavieja.platform.enums;

public enum SexoEnum {
    MASCULINO("M"),FEMENINO("F"),LIBRE("L");
    String sexo;
    private SexoEnum(String sexo) {
        this.sexo = sexo;
    }
    public String getSexo() {
        return sexo;
    }
}
