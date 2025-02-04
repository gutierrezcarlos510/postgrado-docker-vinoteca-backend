package com.casavieja.business.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "zona", schema = "business", catalog = "casa_vieja")
public class ZonaEntity {
    @Id
    @GeneratedValue(generator = "business.zona_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "business.zona_id_seq", sequenceName = "business.zona_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Short id;
    @Basic
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    @Basic
    @Column(name = "status", nullable = false)
    private Boolean status;
    @Basic
    @Column(name = "ciudad_id", nullable = false)
    private Short ciudadId;

    @PrePersist
    public void prePersist() {
        status = true;
    }

}
