package com.casavieja.platform.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.casavieja.platform.enums.TypeUserEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * Tabla de todos los usuarios que interactuan con el sistema
 *
 * @author CARLOS GUTIERREZ
 */
@Getter
@Setter
@Entity
@Table(name = "SYSTEMS_USERS")
public class SystemUser implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Codigo identificador
     */
    @Id
    @Column(name = "id", columnDefinition = "int8")
    private Long id;

    /**
     * Nombrede usuario
     */
    @Basic
    @Column(name = "username",unique = true, columnDefinition = "varchar(50)")
    private String username;

    @Basic
    @Column(name = "celular",unique = true, columnDefinition = "varchar(15)")
    private String celular;

    /**
     * Alias del usuario
     */
    @Basic
    @Column(name = "alias", columnDefinition = "varchar(25)")
    private String alias;

    /**
     * Correo electronico del usuario
     */
    @Basic
    @Column(name = "email", unique = true,columnDefinition = "varchar(100)")
    private String email;

    /**
     * foto de perfil del usuario
     */
    @Basic
    @Column(name = "avatar", columnDefinition = "varchar(50)")
    private String avatar;

    /**
     * Tipo de usuario del sistema: ROOT, ADMIN, CLIENT
     */
    @Basic
    @Column(name = "type_system_user", columnDefinition = "varchar(6)")
    private String typeSystemUser;

    /**
     * Estado activo o inactivo
     */
    @Basic
    @Column(name = "status", columnDefinition = "boolean")
    private Boolean status;

    @PrePersist
    public void perPersist() {
    	this.status = true;
    }

    public void generateAlias() {
    	if(this.email!=null) {
    		int indexArroba = this.email.indexOf("@");
    		if(indexArroba>-1)
    			this.alias = this.email.substring(0,indexArroba);
    		else
    			this.alias = this.email;
    	}
    }
    public String getExtension() {
		String ext = "";
		if(avatar != null) {
			int ind = avatar.lastIndexOf(".");
			if(ind > -1) {
				ext = avatar.substring(ind+1);
			}
		}
		return ext;
	}
    public String getNameAvatar() {
		String nameAvatar = "";
		if(avatar != null) {
			int ind = avatar.lastIndexOf(".");
			if(ind > -1) {
				nameAvatar = avatar.substring(0,ind);
			}
		}
		return nameAvatar;
	}
    public String getnameTypeSystemUser() {
    	if(typeSystemUser!=null) {
    		if(TypeUserEnum.ADMIN.value.equals(typeSystemUser))
    			return "Administrador";
    		if(TypeUserEnum.ROOT.value.equals(typeSystemUser))
    			return "Master";
    		if(TypeUserEnum.CLIENT.value.equals(typeSystemUser))
    			return "Cliente";
    	}
    	return "";
    }
}