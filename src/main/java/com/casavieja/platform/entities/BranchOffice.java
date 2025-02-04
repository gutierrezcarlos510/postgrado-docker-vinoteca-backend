package com.casavieja.platform.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Tabla que guarda las sucursales de una empresa
 *
 * @author CARLOS GUTIERREZ
 */
@Getter
@Setter
@Entity
@Table(name = "BRANCH_OFFICES")
public class BranchOffice implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Codigo identificador
     */
    @Id
    @GeneratedValue(generator = "branch_office_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "branch_office_seq", sequenceName = "branch_office_seq", allocationSize = 1)
    @Column(name = "id", columnDefinition = "int4")
    private Integer id;

    /**
     * nombre de la sucursal
     */
    @NotEmpty
    @Column(name = "name", columnDefinition = "varchar(100)")
    private String name;

    /**
     * Descripcion de la sucursal
     */
    @Column(name = "description", columnDefinition = "varchar(500)")
    private String description;

    /**
     * Direcion de la sucursal
     */
    @Column(name = "address", columnDefinition = "varchar(250)")
    private String address;

    /**
     * Codigo identificador a la empresa que pertenece
     */
    @NotNull
    @Column(name = "company_id", columnDefinition = "int4")
    private Integer companyId;

    /**
     * Estado activo o inactivo
     */
    @NotNull
    @Column(name = "status", columnDefinition = "boolean")
    private Boolean status;
    @Transient
    private Company company;

}