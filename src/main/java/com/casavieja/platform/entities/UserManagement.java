package com.casavieja.platform.entities;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Tabla que guarda la el usuario y sus diferentes sucursales asignadas
 *
 * @author CARLOS GUTIERREZ
 */
@Getter
@Setter
@Entity
@Table(name = "USERS_MANAGEMENTS")
public class UserManagement implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
     * Codigo identificador
     */
    @Id
    @GeneratedValue(generator = "user_management_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_management_seq", sequenceName = "user_management_seq", allocationSize = 1)
    @Column(name = "user_management_id", columnDefinition = "int8")
    private Long userManagementId;

    /**
     * Codigo identificadoe del Usuario del sistema
     */
    @Basic
    @Column(name = "system_user_id", columnDefinition = "int8")
    private Long systemUserId;

    /**
     * Codigo identificador de la sucursal
     */
    @Basic
    @Column(name = "management_id", columnDefinition = "int4")
    private Integer managementId;

    /**
     * Tipo de operacion: readonly=solo lectura,READ_WRITE= lectura y escritura
     */
    @Basic
    @Column(name = "type_operation", columnDefinition = "varchar(15)")
    private String typeOperation;

    /**
     * Estado activo o inactivo
     */
    @Basic
    @Column(name = "status", columnDefinition = "boolean")
    private Boolean status;

}