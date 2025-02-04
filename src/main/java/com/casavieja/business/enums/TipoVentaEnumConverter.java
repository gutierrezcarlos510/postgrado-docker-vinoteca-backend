package com.casavieja.business.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter
public class TipoVentaEnumConverter implements AttributeConverter<TipoVentaE, String> {
    @Override
    public String convertToDatabaseColumn(TipoVentaE attribute) {
        return attribute.getValue();
    }

    @Override
    public TipoVentaE convertToEntityAttribute(String dbData) {
        return Arrays.stream(TipoVentaE.values())
                .filter(s -> s.value.equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
