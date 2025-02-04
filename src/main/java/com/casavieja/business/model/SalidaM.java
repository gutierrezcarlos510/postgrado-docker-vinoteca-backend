package com.casavieja.business.model;

import com.casavieja.business.enums.SalidaE;
import com.casavieja.business.enums.SalidaEnumConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
public class SalidaM {
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
    private boolean status;
    private BigDecimal totalVentaContado;
    private BigDecimal totalOtroTipoPago;
    private BigDecimal totalGastos;
    private BigDecimal totalEfectivoEntregado;
    private BigDecimal totalDescuento;
    private BigDecimal totalImpuesto;
    private BigDecimal totalGeneral;
    private String xdistribuidor;
    private String xusuario;
}
