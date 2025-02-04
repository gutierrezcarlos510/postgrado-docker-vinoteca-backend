package com.casavieja.platform.entities;

import com.casavieja.platform.enums.SexoEnum;
import com.casavieja.platform.enums.SexoEnumConverter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Tabla que agrupa a todas las personas del sistema
 *
 * @author CARLOS GUTIERREZ
 */
@Getter
@Setter
@Entity
@Table(name = "PERSONS")
public class Person implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String SIN_CARNET = "0";
	/**
     * Codigo identificador
     */
    @Id
    @GeneratedValue(generator = "person_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "person_seq", sequenceName = "person_seq", allocationSize = 1)
    @Column(name = "id", columnDefinition = "int8")
    private Long id;

    /**
     * Nombre de la persona
     */
    @Basic
    @Column(name = "name", columnDefinition = "varchar(100)")
    private String name;

    /**
     * Primer apellido
     */
    @Basic
    @Column(name = "first_lastname", columnDefinition = "varchar(100)")
    private String firstLastname;

    /**
     * Segundo apellido
     */
    @Basic
    @Column(name = "second_lastname", columnDefinition = "varchar(100)")
    private String secondLastname;

    /**
     * Genero: M=masculino, F=fenenino
     */
    @Getter(AccessLevel.NONE)
    @Convert(converter = SexoEnumConverter.class)
    private SexoEnum gender;

    /**
     * Carnet de identidad
     */
    @Basic
    @Column(name = "ci", columnDefinition = "varchar(25)")
    @NotEmpty(message = "Este campo no puede estar vacio")
    @Size(min = 1, max = 25, message = "El campo debe estar entre 1 y 25 caracteres")
    private String ci;

    /**
     * Estado activo o inactivo
     */
    @Basic
    @Column(name = "status", columnDefinition = "boolean")
    private Boolean status;

    @Basic
    @Column(name = "codigo_celular", columnDefinition = "varchar(7)")
    @Size(min = 1, max = 7, message = "El campo debe estar entre 1 y 7 caracteres")
    private String codigoCelular;

    @Basic
    @Column(name = "numero_celular", columnDefinition = "varchar(15)")
    @Size(min = 1, max = 15, message = "El campo debe estar entre 1 y 15 caracteres")
    private String numeroCelular;

    @PrePersist
    public void prePersist() {
    	if(ci==null || (ci != null && ci.isEmpty()))
    		this.ci = SIN_CARNET;
    	status = true;
    	upperLetter();
    }
    @PreUpdate
    public void preUpdate() {
    	upperLetter();
    }

    @Basic
    @Column(name = "gender", columnDefinition = "varchar(1)")
    public SexoEnum getGender() {
        return gender;
    }

    public String getFullname() {
    	return name+" "+firstLastname+(secondLastname!=null?(" "+secondLastname):"");
    }

    public void upperLetter() {
    	if(name != null)
    		name = name.toUpperCase();
    	if(firstLastname != null)
    		firstLastname = firstLastname.toUpperCase();
    	if(secondLastname != null)
    		secondLastname = secondLastname.toUpperCase();
    }
}