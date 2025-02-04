package com.casavieja.platform.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter
public class SexoEnumConverter implements AttributeConverter<SexoEnum, String> {

    @Override
    public String convertToDatabaseColumn(SexoEnum attribute) {
        return attribute.getSexo();
    }

    @Override
    public SexoEnum convertToEntityAttribute(String dbData) {
        return Arrays.stream(SexoEnum.values())
                .filter(s -> s.sexo.equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
