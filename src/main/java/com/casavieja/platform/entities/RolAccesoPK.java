package com.casavieja.platform.entities;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class RolAccesoPK implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer rolId,menuId,submenuId;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Objects.equals(getClass(), obj.getClass())) {
            return false;
        }
        final RolAccesoPK other = (RolAccesoPK) obj;
        if (!Objects.equals(this.getRolId(), other.getRolId())) {
            return false;
        }
        if (!Objects.equals(this.getMenuId(), other.getMenuId())) {
            return false;
        }
        if (!Objects.equals(this.getSubmenuId(), other.getSubmenuId())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.getRolId());
        hash = 31 * hash + Objects.hashCode(this.getMenuId());
        hash = 31 * hash + Objects.hashCode(this.getSubmenuId());
        return hash;
    }
}
