package com.casavieja.business.dto.form;

import com.casavieja.business.model.MovimientoInventarioDetalleM;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class MovimientoInventarioForm {
    private Long id;
    private Date fecha;
    private String tipo;
    private Long usuarioId;
    private String motivo;
    private Integer sucursalOrigen;
    private Integer sucursalDestino;
    private Long createdBy;
    private List<MovimientoInventarioDetalleM> detalles;

}
