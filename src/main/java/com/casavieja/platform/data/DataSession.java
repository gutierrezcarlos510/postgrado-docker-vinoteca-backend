/**
 * 
 */
package com.casavieja.platform.data;

import java.io.Serializable;
import java.util.List;

import com.casavieja.platform.entities.AccessKey;
import com.casavieja.platform.entities.Management;
import com.casavieja.platform.entities.Person;
import com.casavieja.platform.entities.Rol;
import com.casavieja.platform.entities.SystemUser;
import com.casavieja.platform.entities.Task;

/**
 * @author CARLOS
 *
 */
public class DataSession implements Serializable {
	private static final long serialVersionUID = 1L;
	private Person person;
	private SystemUser user;
	private AccessKey pass;
	private Management manager;
	private Rol rol;
	private List<Task> taskList;
	private String typeOperation;
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public SystemUser getUser() {
		return user;
	}
	public void setUser(SystemUser user) {
		this.user = user;
	}
	public AccessKey getPass() {
		return pass;
	}
	public void setPass(AccessKey pass) {
		this.pass = pass;
	}
	public Management getManager() {
		return manager;
	}
	public void setManager(Management manager) {
		this.manager = manager;
	}
	public Rol getRol() {
		return rol;
	}
	public void setRol(Rol rol) {
		this.rol = rol;
	}
	public List<Task> getTaskList() {
		return taskList;
	}
	public void setTaskList(List<Task> taskList) {
		this.taskList = taskList;
	}
	public String getTypeOperation() {
		return typeOperation;
	}
	public void setTypeOperation(String typeOperation) {
		this.typeOperation = typeOperation;
	}
	@Override
	public String toString() {
		return "DataSession [person=" + person + ", user=" + user + ", pass=" + pass + ", manager=" + manager + ", rol="
				+ rol + ", taskList=" + taskList + ", typeOperation=" + typeOperation + "]";
	}
}
