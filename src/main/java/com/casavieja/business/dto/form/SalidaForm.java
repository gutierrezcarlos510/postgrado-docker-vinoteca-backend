package com.casavieja.business.dto.form;

import com.casavieja.business.enums.SalidaE;
import com.casavieja.business.enums.SalidaEnumConverter;
import com.casavieja.business.model.ResumenDetalleSalidaPorFinalizarM;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Entity
public class SalidaForm {
    @Id
    private Long id;
    private Date fecha;
    private Long distribuidorId;
    private String obs;
    @Convert(converter = SalidaEnumConverter.class)
    private SalidaE estadoSalida;
    private Integer branchOfficeId;
    private String obsAlFinalizar;
    private Long createdBy;
    private Timestamp createdAt;
    private Long updatedBy;
    private Timestamp updatedAt;
    private BigDecimal totalVentaContado;
    private BigDecimal totalOtroTipoPago;
    private BigDecimal totalGastos;
    private BigDecimal totalEfectivoEntregado;
    private BigDecimal totalDescuento;
    private BigDecimal totalImpuesto;
    private BigDecimal totalGeneral;
    private String xusuario;
    private String xdistribuidor;
    @Transient
    private List<SalidaEntregaForm> entregaList;
    @Transient
    private List<SalidaDetalleResumenForm> resumenProductosList;
    @Transient
    private List<ResumenDetalleSalidaPorFinalizarM> resumenFinalizarList;
}
