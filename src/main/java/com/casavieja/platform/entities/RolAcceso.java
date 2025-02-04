package com.casavieja.platform.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "ROL_ACCESO")
@IdClass(RolAccesoPK.class)
public class RolAcceso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "rol_id", columnDefinition = "int4")
    private Integer rolId;
    @Id
    @Column(name = "menu_id", columnDefinition = "int4")
    private Integer menuId;
    @Id
    @Column(name = "submenu_id", columnDefinition = "int4")
    private Integer submenuId;

}
