package com.casavieja.business.dto.form;

import com.casavieja.business.enums.TipoPedidoE;
import com.casavieja.business.enums.TipoPedidoEnumConverter;
import lombok.Data;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
public class PedidoForm {
    @Id
    private Long id;
    private Long distribuidorId;
    private Long clienteId;
    private Long usuarioId;
    private String observacion;
    private String fechaEntrega;
    private BigDecimal descuento;
    @Convert(converter = TipoPedidoEnumConverter.class)
    private TipoPedidoE tipo;
    private String estado;
    @Transient
    private List<PedidoDetalleForm> detalles;
}
