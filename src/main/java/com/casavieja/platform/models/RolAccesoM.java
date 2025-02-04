package com.casavieja.platform.models;

public class RolAccesoM {
    private Integer rolId,menuId,submenuId;
    private boolean esActivar;

    public Integer getRolId() {
        return rolId;
    }

    public void setRolId(Integer rolId) {
        this.rolId = rolId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getSubmenuId() {
        return submenuId;
    }

    public void setSubmenuId(Integer submenuId) {
        this.submenuId = submenuId;
    }

    public boolean isEsActivar() {
        return esActivar;
    }

    public void setEsActivar(boolean esActivar) {
        this.esActivar = esActivar;
    }
}
