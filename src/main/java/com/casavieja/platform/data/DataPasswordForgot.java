/**
 * 
 */
package com.casavieja.platform.data;

import java.io.Serializable;

/**
 * @author CARLOS
 *
 */
public class DataPasswordForgot implements Serializable{

	private static final long serialVersionUID = 1L;
	private String passwordUser;
	private String passwordUserRepeat;
	private String codeHash;
	public String getPasswordUser() {
		return passwordUser;
	}
	public void setPasswordUser(String passwordUser) {
		this.passwordUser = passwordUser;
	}
	public String getPasswordUserRepeat() {
		return passwordUserRepeat;
	}
	public void setPasswordUserRepeat(String passwordUserRepeat) {
		this.passwordUserRepeat = passwordUserRepeat;
	}
	public String getCodeHash() {
		return codeHash;
	}
	public void setCodeHash(String codeHash) {
		this.codeHash = codeHash;
	}
}
