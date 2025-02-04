package com.casavieja.platform.data;

import java.io.Serializable;

/**
 * 
 * @author CARLOS
 *
 */
public class DataAccessKey implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long systemUserId;
	private String passwordNow;
	private String passwordNew;
	private String passwordNewRepeat;
	public Long getSystemUserId() {
		return systemUserId;
	}
	public void setSystemUserId(Long systemUserId) {
		this.systemUserId = systemUserId;
	}
	public String getPasswordNow() {
		return passwordNow;
	}
	public void setPasswordNow(String passwordNow) {
		this.passwordNow = passwordNow;
	}
	public String getPasswordNew() {
		return passwordNew;
	}
	public void setPasswordNew(String passwordNew) {
		this.passwordNew = passwordNew;
	}
	public String getPasswordNewRepeat() {
		return passwordNewRepeat;
	}
	public void setPasswordNewRepeat(String passwordNewRepeat) {
		this.passwordNewRepeat = passwordNewRepeat;
	}
	@Override
	public String toString() {
		return "DataAccessKey [systemUserId=" + systemUserId + ", passwordNow=" + passwordNow + ", passwordNew="
				+ passwordNew + ", passwordNewRepeat=" + passwordNewRepeat + "]";
	}
	
}
