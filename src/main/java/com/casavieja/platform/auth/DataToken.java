package com.casavieja.platform.auth;

public class DataToken {
    private Long userId;
    private Integer rolActivo;
    private Integer gestionActiva;
    private Integer sucursalActiva;
    private Integer empresaActiva;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getRolActivo() {
        return rolActivo;
    }

    public void setRolActivo(Integer rolActivo) {
        this.rolActivo = rolActivo;
    }

    public Integer getGestionActiva() {
        return gestionActiva;
    }

    public void setGestionActiva(Integer gestionActiva) {
        this.gestionActiva = gestionActiva;
    }

    public Integer getSucursalActiva() {
        return sucursalActiva;
    }

    public void setSucursalActiva(Integer sucursalActiva) {
        this.sucursalActiva = sucursalActiva;
    }

    public Integer getEmpresaActiva() {
        return empresaActiva;
    }

    public void setEmpresaActiva(Integer empresaActiva) {
        this.empresaActiva = empresaActiva;
    }
}
