package com.claro.gestionrecursosapi.reportegenerico.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity()
@Table(name = "user_views")
public class UserViewDb {
	@Id
	@Column(name = "view_name")
	private String viewName;

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
}