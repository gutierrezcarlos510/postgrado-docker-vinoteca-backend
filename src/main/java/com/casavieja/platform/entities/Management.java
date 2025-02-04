package com.casavieja.platform.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Tabla que guarda la relacion de la sucursal y la gestion
 *
 * @author CARLOS GUTIERREZ
 */
@Getter
@Setter
@Entity
@Table(name = "MANAGEMENTS")
public class Management implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Codigo identificador
     */
    @Id
    @GeneratedValue(generator = "management_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "management_seq", sequenceName = "management_seq", allocationSize = 1)
    @Column(name = "id", columnDefinition = "int4")
    private Integer id;

    /**
     * Numero de aÃ±o
     */
    @Basic
    @Column(name = "year_number", columnDefinition = "int2")
    private Short yearNumber;

    /**
     * Codigo identificador de la sucursal
     */
    @Basic
    @Column(name = "branch_office_id", columnDefinition = "int4")
    private Integer branchOfficeId;

    /**
     * Fecha de inicio de la empresa en el sistema
     */
    @Basic
    @Column(name = "start_date", columnDefinition = "date")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    /**
     * Fecha de fin del sistema en el sistema
     */
    @Basic
    @Column(name = "end_date", columnDefinition = "date")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    /**
     * Estado activo o inactivo
     */
    @Basic
    @Column(name = "status", columnDefinition = "boolean")
    private Boolean status;
    @Transient
    private BranchOffice branchOffice;

}