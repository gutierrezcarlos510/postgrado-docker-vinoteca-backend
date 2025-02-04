package com.casavieja.platform.data;

import java.io.Serializable;

import com.casavieja.platform.entities.Person;
import com.casavieja.platform.entities.SystemUser;

public class DataProfile implements Serializable{

	private static final long serialVersionUID = 1L;
	private Person person;
	private SystemUser systemUser;
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public SystemUser getSystemUser() {
		return systemUser;
	}
	public void setSystemUser(SystemUser systemUser) {
		this.systemUser = systemUser;
	}
	@Override
	public String toString() {
		return "DataProfile [person=" + person + ", systemUser=" + systemUser + "]";
	}
}
