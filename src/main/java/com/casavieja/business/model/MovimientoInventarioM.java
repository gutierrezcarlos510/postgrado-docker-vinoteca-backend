package com.casavieja.business.model;

import com.casavieja.business.enums.MovimientoInventarioE;
import com.casavieja.business.enums.MovimientoInventarioEnumConverter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Entity
public class MovimientoInventarioM {
    @Id
    private Long id;
    private Long usuarioId;
    @Convert(converter = MovimientoInventarioEnumConverter.class)
    private MovimientoInventarioE tipo;
    private String motivo;
    private Date fecha;
    private Long createdBy;
    private Timestamp createdAt;
    private Long updatedBy;
    private Timestamp updatedAt;
    private Boolean status;
    private Integer sucursalOrigen;
    private Integer sucursalDestino;
    private String xusuario;
    private String xsucursalOrigen;
    private String xsucursalDestino;
    @Transient
    private List<MovimientoInventarioDetalleM> detalles;
}
