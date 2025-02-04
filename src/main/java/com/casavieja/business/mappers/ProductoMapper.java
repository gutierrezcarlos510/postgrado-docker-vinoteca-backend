package com.casavieja.business.mappers;

import com.casavieja.business.dto.form.ProductoForm;
import com.casavieja.business.entities.ProductoEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductoMapper {

    private final ModelMapper modelMapper;

    public ProductoEntity toEntity(ProductoForm form) {
        return modelMapper.map(form, ProductoEntity.class);
    }

    public ProductoForm toModel(ProductoEntity entity) {
        return modelMapper.map(entity, ProductoForm.class);
    }
}
