package com.casavieja.business.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class ClienteM {
    @Id    private long id;
    private String alias;
    private String direccion;
    private String email;
    private String nombreNegocio;
    private String descripcionNegocio;
    private short tipoCliente;
    private short barrioId;
    private boolean status;
    private String nombreCompleto;
    private String numeroCelular;
}
