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
@Table(name = "MENU_SUBMENU")
@IdClass(MenuSubmenuPK.class)
public class MenuSubmenu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue
    @Column(name = "menu_id", columnDefinition = "int4")
    private Integer menuId;

    @Id
    @Column(name = "submenu_id", columnDefinition = "int4")
    private Integer submenuId;
}