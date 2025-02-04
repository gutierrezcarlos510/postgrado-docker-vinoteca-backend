package com.casavieja.platform.data;

import java.util.List;

public class MenuAssignForm {
    private Integer menuId;
    private List<Integer> submenuList;

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public List<Integer> getSubmenuList() {
        return submenuList;
    }

    public void setSubmenuList(List<Integer> submenuList) {
        this.submenuList = submenuList;
    }
}
