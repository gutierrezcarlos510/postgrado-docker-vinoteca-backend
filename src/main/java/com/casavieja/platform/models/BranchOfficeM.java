package com.casavieja.platform.models;

import com.casavieja.pagination.DataTableRow;
import com.casavieja.platform.entities.Company;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Tabla que guarda las sucursales de una empresa
 *
 * @author CARLOS GUTIERREZ
 */
@Getter
@Setter
@Entity
public class BranchOfficeM extends DataTableRow implements Serializable {

	private static final long serialVersionUID = 1L;
    @Id
    private Integer id;
    private String name;
    private String description;
    private String address;
    private Integer companyId;
    private Boolean status;
    @Transient
    private Company company;
}