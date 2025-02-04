package com.casavieja.platform.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RolMenuPK implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer rolId;

    private Integer menuId;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Objects.equals(getClass(), obj.getClass())) {
            return false;
        }
        final RolMenuPK other = (RolMenuPK) obj;
        if (!Objects.equals(this.getRolId(), other.getRolId())) {
            return false;
        }
        if (!Objects.equals(this.getMenuId(), other.getMenuId())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.getRolId());
        hash = 31 * hash + Objects.hashCode(this.getMenuId());
        return hash;
    }

}