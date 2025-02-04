package com.casavieja.platform.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.casavieja.pagination.DataTableRow;

/**
 * Tabla que guarda la el usuario y sus diferentes sucursales asignadas
 *
 * @author CARLOS GUTIERREZ
 */
@Entity
public class UserManagementM extends DataTableRow implements Serializable {

	private static final long serialVersionUID = 1L;
    @Id
    private Long userManagementId;
    private Long systemUserId;
    private Integer managementId;
    private String typeOperation;

    private Boolean status;

    public Long getUserManagementId() {
        return userManagementId;
    }

    public void setUserManagementId(Long userManagementId) {
        this.userManagementId = userManagementId;
    }

    public Long getSystemUserId() {
        return systemUserId;
    }

    public void setSystemUserId(Long systemUserId) {
        this.systemUserId = systemUserId;
    }

    public Integer getManagementId() {
        return managementId;
    }

    public void setManagementId(Integer managementId) {
        this.managementId = managementId;
    }

    public String getTypeOperation() {
        return typeOperation;
    }

    public void setTypeOperation(String typeOperation) {
        this.typeOperation = typeOperation;
    }

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserManagement{" + " userManagementId=" + userManagementId + ", systemUserId=" + systemUserId + ", managementId=" + managementId + ", typeOperation=" + typeOperation + ", status=" + status + '}';
    }

}