package com.casavieja.platform.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author CARLOS
 */

@Getter
@Setter
@Entity
@Table(name = "ROL_MENU")
@IdClass(RolMenuPK.class)
public class RolMenu implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue
    @Column(name = "rol_id", columnDefinition = "int4")
    private Integer rolId;

    @Id
    @Column(name = "menu_id", columnDefinition = "int4")
    private Integer menuId;
}