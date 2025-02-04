package com.casavieja.business.model;

import com.casavieja.business.entities.MovimientoInventarioDetalleEntityPK;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Getter
@Setter
@Entity
@IdClass(MovimientoInventarioDetalleEntityPK.class)
public class MovimientoInventarioDetalleM {
    @Id
    private Short id;
    private Long movimientoInventarioId;
    private Integer productoId;
    private String lote;
    private String fechaElaboracion;
    private String fechaVencimiento;
    private Integer cantidadUnidad;
    private Integer cantidadCaja;
    private Integer cantidadTotalUnidad;
    private String xproducto;
}
