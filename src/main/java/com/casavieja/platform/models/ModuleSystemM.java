package com.casavieja.platform.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.casavieja.pagination.DataTableRow;
import lombok.Getter;
import lombok.Setter;

/**
 * Modelo que guarda todos los modulos desarrollados en el sistema
 *
 * @author CARLOS GUTIERREZ
 */
@Getter
@Setter
@Entity
public class ModuleSystemM extends DataTableRow implements Serializable {
	private static final long serialVersionUID = 1L;
    @Id
    private Integer id;
    private String codeModule;
    private String name;
    private String description;
    private Boolean status;

}