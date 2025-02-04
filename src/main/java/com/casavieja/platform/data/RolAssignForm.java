package com.casavieja.platform.data;

import java.util.List;

public class RolAssignForm {
    private Integer rolId;
    private List<Integer> menuList;

    public Integer getRolId() {
        return rolId;
    }

    public void setRolId(Integer rolId) {
        this.rolId = rolId;
    }

    public List<Integer> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Integer> menuList) {
        this.menuList = menuList;
    }
}
