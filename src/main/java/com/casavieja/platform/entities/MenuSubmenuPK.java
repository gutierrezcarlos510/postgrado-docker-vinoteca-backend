package com.casavieja.platform.entities;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class MenuSubmenuPK implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer menuId;

    private Integer submenuId;

    public MenuSubmenuPK() {
    }

    public MenuSubmenuPK(Integer menuId, Integer submenuId) {
        this.menuId = menuId;
        this.submenuId = submenuId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Objects.equals(getClass(), obj.getClass())) {
            return false;
        }
        final MenuSubmenuPK other = (MenuSubmenuPK) obj;
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
        hash = 31 * hash + Objects.hashCode(this.getMenuId());
        hash = 31 * hash + Objects.hashCode(this.getSubmenuId());
        return hash;
    }

    @Override
    public String toString() {
        return "MenuSubmenuPK{" + " menuId=" + menuId + ", submenuId=" + submenuId + '}';
    }

}