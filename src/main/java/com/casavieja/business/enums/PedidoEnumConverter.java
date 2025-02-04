package com.casavieja.business.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter
public class PedidoEnumConverter implements AttributeConverter<PedidoE, String> {
    @Override
    public String convertToDatabaseColumn(PedidoE attribute) {
        return attribute.getValue();
    }

    @Override
    public PedidoE convertToEntityAttribute(String dbData) {
        return Arrays.stream(PedidoE.values())
                .filter(s -> s.value.equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
