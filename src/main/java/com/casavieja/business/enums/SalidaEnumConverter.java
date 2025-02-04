package com.casavieja.business.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter
public class SalidaEnumConverter implements AttributeConverter<SalidaE, String> {
    @Override
    public String convertToDatabaseColumn(SalidaE attribute) {
        return attribute.getValue();
    }

    @Override
    public SalidaE convertToEntityAttribute(String dbData) {
        return Arrays.stream(SalidaE.values())
                .filter(s -> s.value.equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
