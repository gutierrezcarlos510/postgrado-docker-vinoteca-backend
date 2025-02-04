package com.casavieja.business.dto.form;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class IngresoForm {
    private Long id;
    private Long numero;
    private Date fecha;
    private Long usuarioRecepcionId;
    private Long usuarioEntregaId;
    private String observacion;
    private Integer branchOfficeId;
    private Long createdBy;
    private int []productos;
    private String []lotes;
    private String []elaboraciones;
    private String []vencimientos;
    private int []unidades;
    private int []cajas;
}
