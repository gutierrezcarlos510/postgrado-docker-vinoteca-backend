package com.casavieja.business.model;

import com.casavieja.business.entities.IngresoDetalleEntityPK;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.sql.Date;

@Getter
@Setter
@Entity
@IdClass(IngresoDetalleEntityPK.class)
public class IngresoDetalleM {
    @Id
    private Short id;
    private Long ingresoId;
    private Integer productoId;
    private String lote;
    private Date fechaElaboracion;
    private Date fechaVencimiento;
    private Integer cantidadUnitaria;
    private Integer cantidadCaja;
    private Integer cantidadTotalUnitaria;
    private String xproducto;
    private Long almacenId;
}
