package com.casavieja.business.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter
public class TipoPedidoEnumConverter implements AttributeConverter<TipoPedidoE, String> {
    @Override
    public String convertToDatabaseColumn(TipoPedidoE attribute) {
        return attribute.getValue();
    }

    @Override
    public TipoPedidoE convertToEntityAttribute(String dbData) {
        return Arrays.stream(TipoPedidoE.values())
                .filter(s -> s.value.equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
