package com.casavieja.platform.models;

import com.casavieja.pagination.DataTableRow;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Modelo que agrupa a todas las personas del sistema
 *
 * @author CARLOS GUTIERREZ
 */
@Getter
@Setter
@Entity
public class PersonM extends DataTableRow implements Serializable {

	private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private String name;
    private String firstLastname;
    private String secondLastname;
    private String gender;
    private String ci;
    private Boolean status;
    private String codigoCelular, numeroCelular;

    public String getFullname() {
    	return name+" "+firstLastname+(secondLastname!=null?(" "+secondLastname):"");
    }
    public String getNameGender() {
    	if(gender!=null) {
    		if(gender.equals("M"))
    			return "Masculino";
    		else
    			return "Femenino";
    	}
    	return "";
    }
}