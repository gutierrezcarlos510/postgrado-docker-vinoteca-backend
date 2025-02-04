package com.casavieja.platform.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@ToString
@Getter
@Setter
@Entity
@Table(name = "CODE_VERIFY")
public class CodeVerify implements Serializable {

    @Id
    @GeneratedValue(generator = "code_verify_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "code_verify_seq", sequenceName = "code_verify_seq", allocationSize = 1)
    @Column(name = "id", columnDefinition = "int4")
    private Integer id;

    @Column(name = "celular", columnDefinition = "varchar(25)")
    private String celular;

    @Column(name = "codigo", columnDefinition = "varchar(6)")
    private String codigo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fini", columnDefinition = "timestamp")
    private Date fini;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ffin", columnDefinition = "timestamp")
    private Date ffin;

    @Column(name = "estado", columnDefinition = "varchar(1)")
    private String estado;

}
