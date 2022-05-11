package com.claro.gestionrecursosapi.reportegenerico.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.claro.gestionrecursosapi.reportegenerico.entity.UserViewDb;

public interface UserViewDbRepository extends CrudRepository<UserViewDb, String> {
	@Query(value = "select userViews from UserViewDb userViews where userViews.viewName like 'DF_%'")
	public Iterable<UserViewDb> findVistasHerramienta();
}