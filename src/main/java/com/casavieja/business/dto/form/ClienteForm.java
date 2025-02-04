package com.casavieja.business.dto.form;

import com.casavieja.platform.enums.SexoEnum;
import com.casavieja.platform.enums.SexoEnumConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Convert;

@Getter
@Setter
public class ClienteForm {
    private Long id;
    private String alias;
    private String direccion;
    private String email;
    private String nombreNegocio;
    private String descripcionNegocio;
    private Short tipoCliente;
    private Short ciudadId, zonaId, barrioId;
    private String name;
    private String firstLastname;
    private String numeroCelular;
    @Convert(converter = SexoEnumConverter.class)
    private SexoEnum gender;
}
