package com.casavieja.pagination;

import javax.persistence.Column;
import javax.persistence.Transient;

public class DataTableRow {
	@Column
	private Integer totalRecords;
	@Transient
	private Integer rn;
	public Integer getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}
	public Integer getRn() {
		return rn;
	}
	public void setRn(Integer rn) {
		this.rn = rn;
	}
}
