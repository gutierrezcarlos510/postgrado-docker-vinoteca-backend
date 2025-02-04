package com.casavieja.platform.entities;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * @author CARLOS
 */
@Getter
@Setter
@Entity
@Table(name = "USER_ROL")
@IdClass(UserRolPK.class)
public class UserRol implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "system_user_id", columnDefinition = "int8")
    private Long systemUserId;

    @Id
    @Column(name = "rol_id", columnDefinition = "int4")
    private Integer rolId;

}