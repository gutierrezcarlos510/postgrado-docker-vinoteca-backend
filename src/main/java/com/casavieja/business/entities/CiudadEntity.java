package com.casavieja.business.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "ciudad", schema = "business", catalog = "casa_vieja")
public class CiudadEntity {
    @Id
    @GeneratedValue(generator = "business.ciudad_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "business.ciudad_id_seq", sequenceName = "business.ciudad_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Short id;
    @Basic
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    @Basic
    @Column(name = "status", nullable = false)
    private Boolean status;

    @PrePersist
    public void prePersist() {
        status = true;
    }
}
