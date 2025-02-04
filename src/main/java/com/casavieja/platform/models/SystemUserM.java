package com.casavieja.platform.models;

import com.casavieja.pagination.DataTableRow;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Modelo de todos los usuarios que interactuan con el sistema
 *
 * @author CARLOS GUTIERREZ
 */
@Getter
@Setter
@Entity
public class SystemUserM extends DataTableRow implements Serializable {

	private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private String username;
    private String alias;
    private String email;
    private String avatar;
    private String typeSystemUser;
    private Boolean status;

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
}