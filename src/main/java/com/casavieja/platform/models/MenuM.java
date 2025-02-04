/**
 * 
 */
package com.casavieja.platform.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author CARLOS
 *
 */
@Getter
@Setter
@Entity
public class MenuM implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	private Integer id;
    private String name;
    private String description;
    private String url;
    private String icon;
    private Boolean status;
}
