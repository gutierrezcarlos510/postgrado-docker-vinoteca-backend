package com.casavieja.platform.models;

import com.casavieja.pagination.DataTableRow;
import com.casavieja.platform.entities.BranchOffice;
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
public class ManagementM extends DataTableRow implements Serializable {

	private static final long serialVersionUID = 1L;
    @Id
    private Integer id;
    private Short yearNumber;
    private Integer branchOfficeId;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    private Boolean status;
    @Transient
    private BranchOffice branchOffice;
}