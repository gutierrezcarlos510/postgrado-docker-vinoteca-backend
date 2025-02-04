package com.casavieja.business.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter
public class FormaPagoEnumConverter implements AttributeConverter<FormaPagoE, Integer> {
    @Override
    public Integer convertToDatabaseColumn(FormaPagoE attribute) {
        return attribute.getValue();
    }

    @Override
    public FormaPagoE convertToEntityAttribute(Integer dbData) {
        return Arrays.stream(FormaPagoE.values())
                .filter(s -> s.value == dbData)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
