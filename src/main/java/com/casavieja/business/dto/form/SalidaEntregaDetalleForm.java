package com.casavieja.business.dto.form;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Data
@Entity
@IdClass(SalidaEntregaDetallePK.class)
public class SalidaEntregaDetalleForm {
    @Id
    private Long salidaId;
    @Id
    private Short salidaEntregaId;
    @Id
    private Short id;
    private Integer productoId;
    private Integer cantidadCaja;
    private Integer cantidadUnitaria;
    private Integer cantidadTotal;
    private String xproducto;
    private Short unidadPorCaja;
}
