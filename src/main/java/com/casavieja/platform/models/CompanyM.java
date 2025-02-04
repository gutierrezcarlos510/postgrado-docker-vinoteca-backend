package com.casavieja.platform.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Modelo
 *
 * @author CARLOS GUTIERREZ
 */
@Getter
@Setter
@Entity
public class CompanyM implements Serializable {

	private static final long serialVersionUID = 1L;
    @Id
    private Integer id;
    private String name;
    private String description;
    private String nit;
    private String place;
    private String phone;
    private String webPage;
    private String email;
    private String fax;
    private Integer typeCompanyId;
    private boolean status;
}