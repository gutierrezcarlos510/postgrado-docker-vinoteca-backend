package com.casavieja.business.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class ZonaM {
    @Id
    private Short id;
    private String nombre;
    private Boolean status;
    private Short ciudadId;
}
