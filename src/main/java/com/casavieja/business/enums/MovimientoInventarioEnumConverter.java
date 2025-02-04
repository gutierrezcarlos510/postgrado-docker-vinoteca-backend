package com.casavieja.business.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter
public class MovimientoInventarioEnumConverter implements AttributeConverter<MovimientoInventarioE, String> {
    @Override
    public String convertToDatabaseColumn(MovimientoInventarioE attribute) {
        return attribute.getValue();
    }

    @Override
    public MovimientoInventarioE convertToEntityAttribute(String dbData) {
        return Arrays.stream(MovimientoInventarioE.values())
                .filter(s -> s.value.equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
