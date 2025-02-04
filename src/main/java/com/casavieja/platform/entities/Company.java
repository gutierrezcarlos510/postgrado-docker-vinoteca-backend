package com.casavieja.platform.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * Tabla que agrupa todas las empresas del sistema
 *
 * @author CARLOS GUTIERREZ
 */
@Getter
@Setter
@Entity
@Table(name = "COMPANIES")
public class Company implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Codigo identificador
     */
    @Id
    @GeneratedValue(generator = "company_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "company_seq", sequenceName = "company_seq", allocationSize = 1)
    @Column(name = "id", columnDefinition = "int4")
    private Integer id;

    /**
     * Nombre de la empresa
     */
    @Basic
    @NotNull(message = "Campo requerido")
    @Column(name = "name", columnDefinition = "varchar(100)")
    private String name;

    /**
     * Descripcion de la empresa
     */
    @Basic
    @Column(name = "description", columnDefinition = "varchar(250)")
    private String description;

    /**
     * Nit de la empresa
     */
    @Basic
    @Column(name = "nit", columnDefinition = "varchar(25)")
    private String nit;

    /**
     * Lugar y pais de la empresa
     */
    @Basic
    @Column(name = "place", columnDefinition = "varchar(100)")
    private String place;

    /**
     * Telefono celular de la empresa
     */
    @Basic
    @NotNull(message = "Campo requerido")
    @Column(name = "phone", columnDefinition = "varchar(100)")
    private String phone;

    /**
     * Pagina web de la empresa
     */
    @Basic
    @Column(name = "web_page", columnDefinition = "varchar(100)")
    private String webPage;

    /**
     * Correo electronico de la empresa
     */
    @Basic
    @Column(name = "email", columnDefinition = "varchar(100)")
    private String email;

    /**
     * Fax de la empresa
     */
    @Basic
    @Column(name = "fax", columnDefinition = "varchar(50)")
    private String fax;

    /**
     * Codigo identificador del tipo de empresa:
     */
    @Basic
    @NotNull(message = "Campo requerido")
    @Column(name = "type_company_id", columnDefinition = "int4")
    private Integer TypeCompanyId;

    /**
     * Estado activo o inactivo
     */
    @Basic
    @NotNull(message = "Campo requerido")
    @Column(name = "status", columnDefinition = "boolean")
    private Boolean status;

    @Transient
    private List<BranchOffice> sucursalList;
}