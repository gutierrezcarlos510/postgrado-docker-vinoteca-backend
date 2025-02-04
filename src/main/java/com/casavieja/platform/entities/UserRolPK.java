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
public class UserRolPK implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long systemUserId;

    private Integer rolId;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Objects.equals(getClass(), obj.getClass())) {
            return false;
        }
        final UserRolPK other = (UserRolPK) obj;
        if (!Objects.equals(this.getSystemUserId(), other.getSystemUserId())) {
            return false;
        }
        if (!Objects.equals(this.getRolId(), other.getRolId())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.getSystemUserId());
        hash = 31 * hash + Objects.hashCode(this.getRolId());
        return hash;
    }

}